package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.UPLOAD_FILE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.upload.InternalAssertions.ASSISTANT_PURPOSE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUploadingAsyncContextStageTest {

    private FileUploadingAsyncContextStage fileUploadingAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.fileUploadingAsyncContextStage =
                new FileUploadingAsyncContextStage(
                UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
                                 .withPurpose(ASSISTANT_PURPOSE_NAME)
        );
    }

    @Test
    public void testFeed_expectCorrectBuilder() {
        assertEquals(
                FILE,
                this.fileUploadingAsyncContextStage.feed(FILE).requestBuilder.file()
        );
    }
}
