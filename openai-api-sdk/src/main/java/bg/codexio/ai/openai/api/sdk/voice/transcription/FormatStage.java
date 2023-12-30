package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionFormat;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;

/**
 * Configures the format of the output file
 */
public class FormatStage
        extends TranscriptionConfigurationStage {

    FormatStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures output file to be just text
     *
     * @return {@link TranscriptionRuntimeSelectionStage}
     */
    public TranscriptionRuntimeSelectionStage justText() {
        return this.formatted(TranscriptionFormat.TEXT);
    }

    /**
     * Configures output file to be for subtitles without metadata .srt
     *
     * @return {@link TranscriptionRuntimeSelectionStage}
     */
    public TranscriptionRuntimeSelectionStage subtitles() {
        return this.formatted(TranscriptionFormat.SUBTITLES_WITHOUT_METADATA);
    }

    /**
     * Configures output file to be for subtitles with metadata .vtt
     *
     * @return {@link TranscriptionRuntimeSelectionStage}
     */
    public TranscriptionRuntimeSelectionStage subtitlesWithMetadata() {
        return this.formatted(TranscriptionFormat.SUBTITLES_WITH_METADATA);
    }

    protected TranscriptionRuntimeSelectionStage formatted(TranscriptionFormat format) {
        return new TranscriptionRuntimeSelectionStage(
                this.executor,
                this.requestBuilder.withFormat(format.val())
        );
    }


}
