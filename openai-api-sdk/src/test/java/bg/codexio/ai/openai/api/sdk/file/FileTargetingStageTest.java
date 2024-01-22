package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTargetingStageTest {

    public static final String ASSISTANT_PURPOSE_NAME = "assistants";
    private FileTargetingStage fileTargetingStage;

    @BeforeEach
    public void setUp() {
        this.fileTargetingStage = new FileTargetingStage(
                null,
                UploadFileRequest.builder()
        );
    }

    @Test
    public void testTargeting_withAssistantPurpose_expectPurposeNameAssistants() {

        var nextStage =
                this.fileTargetingStage.targeting(new AssistantPurpose());

        assertEquals(
                ASSISTANT_PURPOSE_NAME,
                nextStage.requestBuilder.purpose()
        );
    }

    @Test
    public void testForAssistants_expectPurposeNameAssistants() {
        var nextStage = this.fileTargetingStage.forAssistants();

        assertEquals(
                ASSISTANT_PURPOSE_NAME,
                nextStage.requestBuilder.purpose()
        );
    }
}
