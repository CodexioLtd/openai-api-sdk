package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionFormat;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;

/**
 * Configures the format of the output file
 */
public class FormatStage
        extends TranslationConfigurationStage {

    FormatStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures output file to be just text
     *
     * @return {@link TranslationRuntimeSelectionStage}
     */
    public TranslationRuntimeSelectionStage justText() {
        return this.formatted(TranscriptionFormat.TEXT);
    }

    /**
     * Configures output file to be for subtitles without metadata .srt
     *
     * @return {@link TranslationRuntimeSelectionStage}
     */
    public TranslationRuntimeSelectionStage subtitles() {
        return this.formatted(TranscriptionFormat.SUBTITLES_WITHOUT_METADATA);
    }

    /**
     * Configures output file to be for subtitles with metadata .vtt
     *
     * @return {@link TranslationRuntimeSelectionStage}
     */
    public TranslationRuntimeSelectionStage subtitlesWithMetadata() {
        return this.formatted(TranscriptionFormat.SUBTITLES_WITH_METADATA);
    }

    protected TranslationRuntimeSelectionStage formatted(TranscriptionFormat format) {
        return new TranslationRuntimeSelectionStage(
                this.executor,
                this.requestBuilder.withFormat(format.val())
        );
    }


}
