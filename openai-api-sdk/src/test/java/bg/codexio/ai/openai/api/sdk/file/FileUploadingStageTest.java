package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class FileUploadingStageTest {

    private FileUploadingStage fileUploadingStage;

    @BeforeEach
    public void setUp() {
        this.fileUploadingStage = new FileUploadingStage(
                InternalAssertions.UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
                                 .withPurpose(InternalAssertions.ASSISTANT_PURPOSE_NAME)
        );
    }

    @Test
    void testFeedRaw_expectCorrectResponse() {
        when(this.fileUploadingStage.executor.execute(any())).thenAnswer(response -> InternalAssertions.FILE_RESPONSE);

        var response = this.fileUploadingStage.feedRaw(FILE);

        assertEquals(
                InternalAssertions.FILE_RESPONSE,
                response
        );
    }


    @Test
    void testFeed_expectCorrectResponse() {
        when(this.fileUploadingStage.executor.execute(any())).thenAnswer(response -> InternalAssertions.FILE_RESPONSE);

        var fileId = this.fileUploadingStage.feed(FILE);

        assertEquals(
                InternalAssertions.FILE_RESPONSE.id(),
                fileId
        );
    }
}
