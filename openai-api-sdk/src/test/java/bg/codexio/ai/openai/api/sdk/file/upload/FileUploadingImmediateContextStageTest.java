package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.upload.InternalAssertions.ASSISTANT_PURPOSE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileUploadingImmediateContextStageTest {

    private FileUploadingImmediateContextStage fileUploadingImmediateContextStage;

    @BeforeEach
    void setUp() {
        this.fileUploadingImmediateContextStage =
                new FileUploadingImmediateContextStage(
                UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
                                 .withPurpose(ASSISTANT_PURPOSE_NAME)
        );
    }

    @Test
    void testFeedRaw_expectCorrectResponse() {
        assertEquals(
                FILE_RESPONSE,
                this.fileUploadingImmediateContextStage.feedRaw(FILE)
        );
    }

    @Test
    void testFeed_expectCorrectResponse() {
        this.mockImmediateFileExecution();

        assertEquals(
                FILE_RESPONSE.id(),
                this.fileUploadingImmediateContextStage.feed(FILE)
        );
    }

    private void mockImmediateFileExecution() {
        when(UPLOAD_FILE_HTTP_EXECUTOR.execute(any())).thenAnswer(response -> FILE_RESPONSE);
    }
}