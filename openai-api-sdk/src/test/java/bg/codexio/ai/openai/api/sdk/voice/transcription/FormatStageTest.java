package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionFormat;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatStageTest {

    private FormatStage formatStage;

    private static void otherValuesRemainUnchanged(
            TranscriptionConfigurationStage currentStage,
            TranscriptionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> fileRemainsUnchanged(nextStage),
                () -> temperatureRemainsUnchanged(nextStage),
                () -> languageRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.formatStage = new FormatStage(
                null,
                TranscriptionRequest.builder()
                                    .withModel(TEST_MODEL.name())
                                    .withFile(TEST_FILE)
                                    .withTemperature(TEST_TEMPERATURE.val())
                                    .withLanguage(TEST_LANGUAGE)
        );
    }

    @Test
    public void testJustText_expectOutputText() {
        var nextStage = this.formatStage.justText();

        assertAll(
                () -> assertEquals(
                        TranscriptionFormat.TEXT.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.formatStage,
                        nextStage
                )
        );
    }

    @Test
    public void testSubtitles_expectOutputSrt() {
        var nextStage = this.formatStage.subtitles();
        assertAll(
                () -> assertEquals(
                        TranscriptionFormat.SUBTITLES_WITHOUT_METADATA.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.formatStage,
                        nextStage
                )
        );
    }

    @Test
    public void testSubtitlesWithMetadata_expectOutputVtt() {
        var nextStage = this.formatStage.subtitlesWithMetadata();
        assertAll(
                () -> assertEquals(
                        TranscriptionFormat.SUBTITLES_WITH_METADATA.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.formatStage,
                        nextStage
                )
        );
    }

    @ParameterizedTest
    @EnumSource(TranscriptionFormat.class)
    public void testFormatted_withAllEnumValues_expectOutputEnumsValue(TranscriptionFormat format) {
        var nextStage = this.formatStage.formatted(format);

        assertAll(
                () -> assertEquals(
                        format.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.formatStage,
                        nextStage
                )
        );
    }
}
