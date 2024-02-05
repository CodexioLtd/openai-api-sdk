package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileDownloadingNameTypeStageTest {

    private FileDownloadingNameTypeStage<FileContentResponse> fileDownloadingNameTypeStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingNameTypeStage = new FileDownloadingNameTypeStage<>(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                UploadFileRequest.builder(),
                FILE_TEST_ID
        );
    }

    @Test
    void testAs_expectCorrectBuilder() {
        var nextStage = this.fileDownloadingNameTypeStage.as(FILE_TEST_NAME);

        assertNotNull(nextStage);
    }
}