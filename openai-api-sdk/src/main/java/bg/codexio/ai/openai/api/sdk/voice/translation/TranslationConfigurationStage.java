package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;

/**
 * Base for all Translation stages
 */
public abstract class TranslationConfigurationStage {

    protected final TranslationHttpExecutor executor;
    protected final TranslationRequest.Builder requestBuilder;

    TranslationConfigurationStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}
