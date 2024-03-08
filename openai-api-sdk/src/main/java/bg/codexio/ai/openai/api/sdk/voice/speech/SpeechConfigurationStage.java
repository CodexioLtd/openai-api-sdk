package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;

/**
 * Base for all Speech stages
 */
public abstract class SpeechConfigurationStage {

    protected final SpeechHttpExecutor executor;
    protected final SpeechRequest.Builder requestBuilder;

    SpeechConfigurationStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}