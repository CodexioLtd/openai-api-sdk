package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.UPLOAD_FILE_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileTargetingStageTest {

    public static final String ASSISTANT_PURPOSE_NAME = "assistants";
    private FileTargetingStage<FileResponse> fileTargetingStage;

    @BeforeEach
    public void setUp() {
        this.fileTargetingStage = new FileTargetingStage<>(
                UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
        );
    }

    @Test
    public void testTargeting_withAssistantPurpose_expectPurposeNameAssistants() {
        var nextStage =
                this.fileTargetingStage.targeting(new AssistantPurpose());

        assertNotNull(nextStage);
    }

    @Test
    public void testForAssistants_expectPurposeNameAssistants() {
        var nextStage = this.fileTargetingStage.forAssistants();

        assertNotNull(nextStage);
    }
}
