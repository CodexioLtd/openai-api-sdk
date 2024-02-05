package bg.codexio.ai.openai.api.sdk.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileActionTypeStageTest {

    private FileActionTypeStage fileActionTypeStage;

    @BeforeEach
    void setUp() {
        this.fileActionTypeStage = new FileActionTypeStage(
                UPLOAD_FILE_HTTP_EXECUTOR,
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
        );
    }

    @Test
    void testUpload_expectCorrectBuilder() {
        var nextStage = this.fileActionTypeStage.upload();

        assertNotNull(nextStage);
    }

    @Test
    void testDownload_withFileId_expectCorrectBuilder() {
        var nextStage = this.fileActionTypeStage.download(FILE_TEST_ID);

        assertNotNull(nextStage);
    }

    @Test
    void testDownload_withFileResult_expectCorrectBuilder() {
        var nextStage = this.fileActionTypeStage.download(FILE_RESULT);

        assertNotNull(nextStage);
    }

    @Test
    void testDownload_withFileResponse_expectCorrectBuilder() {
        var nextStage = this.fileActionTypeStage.download(FILE_RESPONSE);

        assertNotNull(nextStage);
    }
}