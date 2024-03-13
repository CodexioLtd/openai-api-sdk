package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.UPLOAD_FILE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.upload.InternalAssertions.ASSISTANT_PURPOSE_NAME;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileUploadingRuntimeSelectionStageTest {

    private FileUploadingRuntimeSelectionStage fileUploadingRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.fileUploadingRuntimeSelectionStage =
                new FileUploadingRuntimeSelectionStage(
                UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
                                 .withPurpose(ASSISTANT_PURPOSE_NAME)
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        assertNotNull(this.fileUploadingRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        assertNotNull(this.fileUploadingRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        assertNotNull(this.fileUploadingRuntimeSelectionStage.reactive());
    }
}
