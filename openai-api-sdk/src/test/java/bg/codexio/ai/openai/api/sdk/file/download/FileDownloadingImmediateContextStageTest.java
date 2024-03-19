package bg.codexio.ai.openai.api.sdk.file.download;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileDownloadingImmediateContextStageTest {

    private FileDownloadingImmediateContextStage fileDownloadingStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingStage = new FileDownloadingImmediateContextStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FILE_DOWNLOADING_META_WITHOUT_FOLDER
        );
    }

    @Test
    void testToFolder_expectCorrectResponse() {
        when(RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR.executeWithPathVariables(any())).thenReturn(FILE_CONTENT_RESPONSE);

        mockDownloadExecutor(() -> {
            try {
                assertEquals(
                        FILE_TEST_PATH.replace(
                                "/",
                                File.separator
                        ),
                        this.fileDownloadingStage.toFolder(FILE)
                                                 .getPath()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}