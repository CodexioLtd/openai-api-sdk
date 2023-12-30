package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimplifiedStageTest {
    private static final ModelTypeAbstract SIMPLIFIED_MODEL_TYPE =
            new GPT35TurboModel();
    private SimplifiedStage simplifiedStage;

    @BeforeEach
    public void setUp() {
        this.simplifiedStage =
                new SimplifiedStage(FromDeveloper.doPass(new ApiCredentials(
                        "")));
    }

    @Test
    public void testEnsureRuntimeSelectionStage_expectFilledRuntimeSelectionStage() {
        var stage = this.simplifiedStage.simply();

        assertAll(
                () -> assertEquals(
                        SIMPLIFIED_MODEL_TYPE.name(),
                        stage.requestBuilder.model()
                ),
                () -> assertEquals(
                        Creativity.INTRODUCE_SOME_RANDOMNESS.val(),
                        stage.requestBuilder.temperature()
                )
        );
    }
}
