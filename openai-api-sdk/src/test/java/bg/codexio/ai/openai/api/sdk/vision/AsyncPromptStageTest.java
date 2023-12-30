package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.request.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncPromptStageTest {

    private AsyncPromptStage promptStage;

    private static void otherValuesRemainUnchanged(
            VisionConfigurationStage currentStage,
            VisionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> tokensRemainUnchanged(nextStage),
                () -> imageRemainsUnchanged(
                        nextStage,
                        0
                ),
                () -> detailsRemainsUnchanged(
                        nextStage,
                        0
                ),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    private static String getRequestMessage(VisionConfigurationStage nextStage) {
        return (
                (QuestionVisionRequest) nextStage.requestContext.messages()
                                                                .get(0)
                                                                .content()
                                                                .get(1)
        ).text();
    }

    @BeforeEach
    public void setUp() {
        this.promptStage = new AsyncPromptStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
                             .withTokens(TEST_TOKENS)
                             .withMessages(new MessageContentHolder(List.of(new ImageUrlMessageRequest(new ImageUrlRequest(
                                     TEST_IMAGE,
                                     TEST_ANALYZE
                             )))))
        );
    }

    @Test
    public void testDescribe_withoutPrompt_expectDefaultPrompt() {
        var nextStage = this.promptStage.describe();

        assertAll(
                () -> assertEquals(
                        "What’s in this image?",
                        getRequestMessage(nextStage)
                ),
                () -> otherValuesRemainUnchanged(
                        this.promptStage,
                        nextStage
                )
        );
    }

    @Test
    public void testDescribe_expectManualPrompt() {
        var nextStage = this.promptStage.describe("What is this?");

        assertAll(
                () -> assertEquals(
                        "What is this?",
                        getRequestMessage(nextStage)
                ),
                () -> otherValuesRemainUnchanged(
                        this.promptStage,
                        nextStage
                )
        );
    }

}
