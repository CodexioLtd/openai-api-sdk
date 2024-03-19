package bg.codexio.ai.openai.api.sdk.file.download;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_DOWNLOADING_META_WITHOUT_FOLDER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDownloadingAsyncContextStageTest {

    private FileDownloadingAsyncContextStage fileDownloadingAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingAsyncContextStage =
                new FileDownloadingAsyncContextStage(
                        RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                        FILE_DOWNLOADING_META_WITHOUT_FOLDER
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
