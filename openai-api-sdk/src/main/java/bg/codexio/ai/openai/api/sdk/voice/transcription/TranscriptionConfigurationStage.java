package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;

/**
 * Base for all Transcription stages
 */
public abstract class TranscriptionConfigurationStage {

    protected final TranscriptionHttpExecutor executor;
    protected final TranscriptionRequest.Builder requestBuilder;

    TranscriptionConfigurationStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}
