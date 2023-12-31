package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.TEST_FILE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreSimplifiedStageTest {

    private PreSimplifiedStage preSimplifiedStage;

    @BeforeEach
    public void setUp() {
        this.preSimplifiedStage = new PreSimplifiedStage(new ApiCredentials(
                "testKey"));
    }

    @Test
    public void testEnsureRuntimeSelectionStage_expectFilledRuntimeSelectionStage() {
        var nextStage = this.preSimplifiedStage.translate(TEST_FILE);

        assertEquals(
                TEST_FILE,
                nextStage.requestBuilder.file()
        );
    }

}
