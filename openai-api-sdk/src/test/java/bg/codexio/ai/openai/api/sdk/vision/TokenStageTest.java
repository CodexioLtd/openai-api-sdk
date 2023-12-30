package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class TokenStageTest {

    private TokenStage tokenStage;

    private static void otherValuesRemainUnchanged(
            VisionConfigurationStage currentStage,
            VisionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.tokenStage = new TokenStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
        );
    }

    @Test
    public void testLimitResponseTo_expectManualSelection() {
        var nextStage = this.tokenStage.limitResponseTo(TEST_TOKENS);

        assertAll(
                () -> assertEquals(
                        TEST_TOKENS,
                        nextStage.requestContext.maxTokens()
                ),
                () -> otherValuesRemainUnchanged(
                        this.tokenStage,
                        nextStage
                )
        );
    }

    @Test
    public void tesDefaultTokens_expectNullMaxTokens() {
        var nextStage = this.tokenStage.defaultTokens();

        assertAll(
                () -> assertNull(nextStage.requestContext.maxTokens()),
                () -> otherValuesRemainUnchanged(
                        this.tokenStage,
                        nextStage
                )
        );
    }

    @Test
    public void testMaxTokens_expectIntMax() {
        var nextStage = this.tokenStage.maxTokens();

        assertAll(
                () -> assertEquals(
                        4096,
                        nextStage.requestContext.maxTokens()
                ),
                () -> otherValuesRemainUnchanged(
                        this.tokenStage,
                        nextStage
                )
        );
    }
}
