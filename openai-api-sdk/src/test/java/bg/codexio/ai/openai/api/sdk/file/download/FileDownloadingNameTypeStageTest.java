package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_TEST_NAME;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileDownloadingNameTypeStageTest {

    private FileDownloadingNameTypeStage fileDownloadingNameTypeStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingNameTypeStage = new FileDownloadingNameTypeStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FileDownloadingMeta.builder()
        );
    }

    @Test
    void testAs_expectCorrectBuilder() {
        var nextStage = this.fileDownloadingNameTypeStage.as(FILE_TEST_NAME);

        assertNotNull(nextStage);
    }
}