package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanguageStageTest {

    private LanguageStage languageStage;

    private static void otherValuesRemainUnchanged(
            TranscriptionConfigurationStage currentStage,
            TranscriptionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> fileRemainsUnchanged(nextStage),
                () -> temperatureRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.languageStage = new LanguageStage(
                TEST_EXECUTOR,
                TranscriptionRequest.builder()
                                    .withModel(TEST_MODEL.name())
                                    .withFile(TEST_FILE)
                                    .withTemperature(TEST_TEMPERATURE.val())
        );
    }

    @Test
    public void testTalkingInEnglish_expectEnLanguage() {
        var nextStage = this.languageStage.talkingInEnglish();

        assertAll(
                () -> assertEquals(
                        "en",
                        nextStage.requestBuilder.language()
                ),
                () -> otherValuesRemainUnchanged(
                        this.languageStage,
                        nextStage
                )
        );
    }

    @Test
    public void testTalkingInDeutsch_expectDeLanguage() {
        var nextStage = this.languageStage.talkingInDeutsch();

        assertAll(
                () -> assertEquals(
                        "de",
                        nextStage.requestBuilder.language()
                ),
                () -> otherValuesRemainUnchanged(
                        this.languageStage,
                        nextStage
                )
        );
    }

    @Test
    public void testTalkingIn_withGivenInput_expectSameLanguage() {
        var nextStage = this.languageStage.talkingIn("bg");

        assertAll(
                () -> assertEquals(
                        "bg",
                        nextStage.requestBuilder.language()
                ),
                () -> otherValuesRemainUnchanged(
                        this.languageStage,
                        nextStage
                )
        );
    }

}
