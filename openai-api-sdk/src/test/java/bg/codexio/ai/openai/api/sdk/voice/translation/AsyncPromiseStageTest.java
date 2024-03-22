package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class AsyncPromiseStageTest {

    private final TranslationHttpExecutor httpExecutor = TEST_EXECUTOR;

    private AsyncPromiseStage asyncPromiseStage;

    private static void mockExecutorToExecuteCallbacks(
            TranslationHttpExecutor executor
    ) {
        doAnswer(invocation -> {
            var rawResponse = invocation.getArgument(0)
                                        .toString();

            var callback = invocation.getArgument(
                    1,
                    Consumer.class
            );
            callback.accept(rawResponse.substring(
                    0,
                    rawResponse.length() / 2
            ));
            callback.accept(rawResponse.substring(rawResponse.length() / 2));

            var finalizer = invocation.getArgument(
                    2,
                    Consumer.class
            );
            finalizer.accept(new SpeechTextResponse(rawResponse));

            return null;
        }).when(executor)
          .async()
          .execute(
                  any(),
                  any(),
                  any()
          );
    }

    @BeforeEach
    public void setUp() {
        this.asyncPromiseStage = new AsyncPromiseStage(
                this.httpExecutor,
                TranslationRequest.builder()
                                  .withModel(TEST_MODEL.name())
                                  .withTemperature(TEST_TEMPERATURE.val())
                                  .withFormat(TEST_AUDIO.val())
                                  .withPrompt(TEST_INPUT)
                                  .withFile(TEST_FILE)
        );
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        mockExecutorToExecuteCallbacks(this.httpExecutor);
        var callback = mock(Consumer.class);
        this.asyncPromiseStage.onEachLine(callback);

        verify(
                callback,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedWithCorrectFileMock() {
        mockExecutorToExecuteCallbacks(this.httpExecutor);
        var finalizer = spy(new SpeechConsumer());
        this.asyncPromiseStage.then(finalizer);

        verify(finalizer).accept(new SpeechTextResponse(this.asyncPromiseStage.requestBuilder.build()
                                                                                             .toString()));
    }

    @Test
    public void testThen_withEachLinkeAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        mockExecutorToExecuteCallbacks(this.httpExecutor);
        var finalizer = spy(new SpeechConsumer());
        var callback = mock(Consumer.class);

        this.asyncPromiseStage.then(
                callback,
                finalizer
        );

        assertAll(
                () -> verify(
                        callback,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(new SpeechTextResponse(this.asyncPromiseStage.requestBuilder.build()
                                                                                                           .toString()))
        );
    }
}

class SpeechConsumer
        implements Consumer<SpeechTextResponse> {

    @Override
    public void accept(SpeechTextResponse speechTextResponse) {
        // no-op
    }
}
