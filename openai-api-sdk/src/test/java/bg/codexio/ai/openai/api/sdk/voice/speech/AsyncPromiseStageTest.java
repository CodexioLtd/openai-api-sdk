package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.payload.voice.response.AudioBinaryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AsyncPromiseStageTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final File TARGET_FOLDER = new File("imaginaryFolder");
    private static final File FILE_OUTPUT = new File("/var/files/resultFile");
    private static Logger LOGGER;
    private final SpeechHttpExecutor httpExecutor = TEST_EXECUTOR;

    private AsyncPromiseStage asyncPromiseStage;

    private static void mockExecutorToExecuteCallbacks(
            SpeechHttpExecutor executor,
            AudioBinaryResponse response,
            boolean error,
            Runnable execution
    ) throws JsonProcessingException {
        var rawResponse = OBJECT_MAPPER.writeValueAsString(response);

        var spiedFuture = spy(new CompletableFuture<>());

        try (
                var downloadUtils = mockStatic(DownloadExecutor.class);
                var futureUtils = mockStatic(CompletableFuture.class)
        ) {
            // always return spied future which whenComplete will receive the
            // result of the supplier
            futureUtils.when(() -> CompletableFuture.supplyAsync(any()))
                       .thenAnswer(invocation -> {
                           var supplier = invocation.getArgument(
                                   0,
                                   Supplier.class
                           );
                           if (ExecutionRegistry.CURRENT_EXECUTION
                                   == Execution.PASSTHRU) {
                               try {
                                   spiedFuture.complete(supplier.get());
                               } catch (Throwable t) {
                                   spiedFuture.completeExceptionally(new RuntimeException(
                                           t.getMessage(),
                                           t
                                   ));
                               }
                           } else {
                               spiedFuture.complete(supplier.get());
                           }

                           return spiedFuture;
                       });

            var downloadStub =
                    downloadUtils.when(() -> DownloadExecutor.downloadTo(
                    eq(TARGET_FOLDER),
                    eq(response),
                    eq(AudioFormat.MP3.val())
            ));
            if (error) {
                // always return error
                downloadStub.thenThrow(new IOException("Error while downloading."));
            } else {
                // always return previously setup file if all parameters are
                // correct
                downloadStub.thenReturn(FILE_OUTPUT);
            }

            // call onEachLines with two halves of the response and finalizer
            // with whole response
            doAnswer(invocation -> {
                var callback = invocation.getArgument(
                        1,
                        Consumer.class
                );
                callback.accept(rawResponse.substring(
                        0,
                        rawResponse.length() / 2
                ));
                callback.accept(rawResponse.substring(
                        rawResponse.length() / 2));

                var finalizer = invocation.getArgument(
                        2,
                        Consumer.class
                );
                finalizer.accept(response);

                return null;
            }).when(executor)
              .executeAsync(
                      any(),
                      any(),
                      any()
              );

            execution.run();
        }

    }

    @BeforeEach
    public void setUp() {
        ExecutionRegistry.CURRENT_EXECUTION = Execution.PASSTHRU;
        LOGGER = mock(Logger.class);

        this.asyncPromiseStage = new AsyncPromiseStage(
                this.httpExecutor,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
                             .withVoice(TEST_SPEAKER.val())
                             .withFormat(TEST_AUDIO.val())
                             .withSpeed(TEST_SPEED.val())
                             .withInput(TEST_INPUT),
                new File("imaginaryFolder"),
                LOGGER
        );
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine()
            throws IOException {
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                new AudioBinaryResponse(new byte[]{
                        1, 2, 3
                }),
                false,
                () -> {
                    var lineData = prepareCallback(String.class);
                    this.asyncPromiseStage.onEachLine(lineData.callback());

                    verify(
                            lineData.callback(),
                            times(2)
                    ).accept(any());
                }
        );
    }

    @Test
    public void testOnEachLine_withMockConsumerAndErrorCallback_shouldCallConsumerForEachLineNoError()
            throws IOException {
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                new AudioBinaryResponse(new byte[]{
                        1, 2, 3
                }),
                false,
                () -> {
                    var lineData = prepareCallback(String.class);
                    var errorData = prepareCallback(Throwable.class);
                    this.asyncPromiseStage.onEachLine(
                            lineData.callback(),
                            errorData.callback()
                    );

                    assertAll(
                            () -> verify(
                                    lineData.callback(),
                                    times(2)
                            ).accept(any()),
                            () -> verifyNoInteractions(errorData.callback())
                    );
                }
        );
    }

    @Test
    public void testThen_withFinalizerAndErrorCallback_shouldBeInvokedWithCorrectFileMockNoError()
            throws IOException {
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                false,
                () -> {
                    var finalizerData = prepareCallback(File.class);
                    var errorData = prepareCallback(Throwable.class);

                    this.asyncPromiseStage.then(
                            finalizerData.callback(),
                            errorData.callback()
                    );

                    assertAll(
                            () -> verify(finalizerData.callback()).accept(eq(FILE_OUTPUT)),
                            () -> verifyNoInteractions(errorData.callback())
                    );
                }
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedWithCorrectFileMock()
            throws IOException {
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                false,
                () -> {
                    var finalizerData = prepareCallback(File.class);

                    this.asyncPromiseStage.then(finalizerData.callback());

                    verify(finalizerData.callback()).accept(eq(FILE_OUTPUT));
                }
        );
    }

    @Test
    public void testThen_withEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines()
            throws IOException {
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                false,
                () -> {
                    var finalizerData = prepareCallback(File.class);
                    var lineData = prepareCallback(String.class);
                    var errorData = prepareCallback(Throwable.class);

                    this.asyncPromiseStage.then(
                            lineData.callback(),
                            finalizerData.callback(),
                            errorData.callback()
                    );

                    assertAll(
                            () -> verify(
                                    lineData.callback(),
                                    times(2)
                            ).accept(any()),
                            () -> verify(finalizerData.callback()).accept(eq(FILE_OUTPUT)),
                            () -> verifyNoInteractions(errorData.callback())
                    );
                }
        );
    }

    @Test
    public void testThen_withEachLineFinalizerAndErrorCallback_expectError()
            throws JsonProcessingException {
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                true,
                () -> {
                    var finalizerData = prepareCallback(File.class);
                    var lineData = prepareCallback(String.class);
                    var errorData = prepareCallback(Throwable.class);

                    this.asyncPromiseStage.then(
                            lineData.callback(),
                            finalizerData.callback(),
                            errorData.callback()
                    );

                    assertAll(
                            () -> assertEquals(
                                    RuntimeException.class,
                                    errorData.data()
                                             .getClass()
                            ),
                            () -> assertEquals(
                                    "java.io.IOException: Error while "
                                            + "downloading.",
                                    errorData.data()
                                             .getMessage()
                            )
                    );
                }
        );
    }

    @Test
    public void testThen_withEachLineAndFinalizerRethrow_expectError()
            throws JsonProcessingException {
        ExecutionRegistry.CURRENT_EXECUTION = Execution.RETHROW;

        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                true,
                () -> {
                    var finalizerData = prepareCallback(File.class);
                    var lineData = prepareCallback(String.class);
                    var errorData = prepareCallback(Throwable.class);

                    var exception = assertThrows(
                            RuntimeException.class,
                            () -> this.asyncPromiseStage.then(
                                    lineData.callback(),
                                    finalizerData.callback(),
                                    errorData.callback()
                            )
                    );

                    assertEquals(
                            "java.io.IOException: Error while downloading.",
                            exception.getMessage()
                    );
                }
        );
    }

    @Test
    public void testThen_withFinalizer_expectError()
            throws JsonProcessingException {
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                true,
                () -> {
                    var finalizerData = prepareCallback(File.class);

                    this.asyncPromiseStage.then(finalizerData.callback());

                    verify(
                            LOGGER,
                            times(1)
                    ).error(
                            eq("Download error."),
                            any(RuntimeException.class)
                    );
                }
        );
    }

    @Test
    public void testThen_withEachLine_expectError()
            throws JsonProcessingException {
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        mockExecutorToExecuteCallbacks(
                this.httpExecutor,
                response,
                true,
                () -> {
                    var onEachLine = prepareCallback(String.class);

                    this.asyncPromiseStage.onEachLine(onEachLine.callback());

                    verify(
                            LOGGER,
                            times(1)
                    ).error(
                            eq("Download error."),
                            any(RuntimeException.class)
                    );
                }
        );
    }

    enum Execution {
        PASSTHRU,
        RETHROW
    }

    private static class ExecutionRegistry {
        static Execution CURRENT_EXECUTION = Execution.PASSTHRU;
    }
}
