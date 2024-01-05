package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.payload.voice.response.AudioBinaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.OngoingStubbing;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SynchronousDownloadStageTest {

    private final SpeechHttpExecutor httpExecutor = TEST_EXECUTOR;

    private SynchronousDownloadStage synchronousDownloadStage;

    @BeforeEach
    public void setUp() {
        this.synchronousDownloadStage = new SynchronousDownloadStage(
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
    public void testDownloadTo_withImaginaryFolder_shouldUseCorrectMediaTypeAndResponse()
            throws IOException {
        try (var mockData = startMocking()) {
            var filePath = "var/files/resultFile";
            mockData.executorMock()
                    .thenReturn(new File(filePath));

            var result =
                    this.synchronousDownloadStage.downloadTo(mockData.targetFolder());
            assertEquals(
                    filePath.replace(
                            "/",
                            File.separator
                    ),
                    result.getPath()
            );
        }
    }

    @Test
    public void testDownloadTo_withErrorWhileDownloading_shouldThrowException() {
        try (var mockData = startMocking()) {
            mockData.executorMock()
                    .thenThrow(new RuntimeException("Cannot download"));

            var exception = assertThrows(
                    RuntimeException.class,
                    () -> this.synchronousDownloadStage.downloadTo(mockData.targetFolder())
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

        when(this.httpExecutor.execute(any())).thenReturn(response);

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

    static class MockData
            implements AutoCloseable {
        private final OngoingStubbing<Object> executorMock;
        private final File targetFolder;
        private final MockedStatic<DownloadExecutor> utils;

        public MockData(
                OngoingStubbing<Object> executorMock,
                File targetFolder,
                MockedStatic<DownloadExecutor> utils
        ) {
            this.executorMock = executorMock;
            this.targetFolder = targetFolder;
            this.utils = utils;
        }

        public OngoingStubbing<Object> executorMock() {
            return executorMock;
        }

        public File targetFolder() {
            return targetFolder;
        }

        public MockedStatic<DownloadExecutor> utils() {
            return utils;
        }

        @Override
        public void close() {
            utils().close();
        }
    }
}
