package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_TEST_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDownloadingAsyncContextStageTest {

    private FileDownloadingAsyncContextStage fileDownloadingAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingAsyncContextStage =
                new FileDownloadingAsyncContextStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FileDownloadingMeta.builder()
                                   .withFileName(FILE_TEST_NAME)
                                   .withFileId(FILE_TEST_ID)
        );
    }

    @Test
    public void testDownloadTo_expectCorrectBuilder() {
        assertEquals(
                FILE,
                this.fileDownloadingAsyncContextStage.downloadTo(FILE).fileDownloadingMeta.targetFolder()
        );
    }
}
