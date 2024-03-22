package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor;
import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.payload.voice.response.AudioBinaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.OngoingStubbing;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ReactiveDownloadStageTest {

    private final SpeechHttpExecutor httpExecutor = TEST_EXECUTOR;

    private ReactiveDownloadStage reactiveDownloadStage;


    @BeforeEach
    public void setUp() {
        this.reactiveDownloadStage = new ReactiveDownloadStage(
                this.httpExecutor,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
                             .withVoice(TEST_SPEAKER.val())
                             .withFormat(TEST_AUDIO.val())
                             .withSpeed(TEST_SPEED.val())
                             .withInput(TEST_INPUT)
        );
    }

    @Test
    public void testDownloadTo_withImaginaryFolder_shouldUseCorrectMediaTypeAndResponse() {
        try (var mockData = this.startMocking()) {
            var filePath = "var/files/resultFile";

            mockData.executorMock()
                    .thenReturn(new File(filePath));

            var result =
                    this.reactiveDownloadStage.downloadTo(mockData.targetFolder());
            assertEquals(
                    filePath.replace(
                            "/",
                            File.separator
                    ),
                    result.block()
                          .getPath()
            );
        }
    }

    @Test
    public void testDownloadTo_withErrorWhileDownloading_shouldThrowException() {
        try (var mockData = this.startMocking()) {
            mockData.executorMock()
                    .thenThrow(new RuntimeException("Cannot download"));

            var result =
                    this.reactiveDownloadStage.downloadTo(mockData.targetFolder());
            var exception = assertThrows(
                    RuntimeException.class,
                    () -> result.block()
                                .getAbsolutePath()
            );
            assertEquals(
                    "Cannot download",
                    exception.getMessage()
            );
        }
    }

    private MockData startMocking() {
        var targetFolder = new File("imaginaryFolder");
        var response = new AudioBinaryResponse(new byte[]{1, 2, 3});
        var reactiveExecution = new ReactiveHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(response)
        );

        when(this.httpExecutor.reactive()
                              .execute(any())).thenReturn(reactiveExecution);

        var downloadUtils = mockStatic(DownloadExecutor.class);

        return new MockData(
                downloadUtils.when(() -> DownloadExecutor.downloadTo(
                        eq(targetFolder),
                        eq(response),
                        eq(AudioFormat.MP3.val())
                )),
                targetFolder,
                downloadUtils
        );
    }

    record MockData(
            OngoingStubbing<Object> executorMock,
            File targetFolder,
            MockedStatic<DownloadExecutor> utils
    )
            implements AutoCloseable {
        @Override
        public void close() {
            this.utils()
                .close();
        }
    }
}
