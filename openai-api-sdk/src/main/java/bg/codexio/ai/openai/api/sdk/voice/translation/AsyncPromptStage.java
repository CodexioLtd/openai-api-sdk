package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Registers user input optionally
 */
public class AsyncPromptStage
        extends TranslationConfigurationStage
        implements RuntimeExecutor {

    AsyncPromptStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Guides the translation with additional prompt
     *
     * @return {@link AsyncPromiseStage} to execute the request with callbacks
     */
    public AsyncPromiseStage guide(String prompt) {
        return new AsyncPromiseStage(
                this.executor,
                this.requestBuilder.withPrompt(prompt)
        );
    }

    /**
     * No additional prompt is given to the OpenAI API
     *
     * @return {@link AsyncPromiseStage} to execute the request with callbacks
     */
    public AsyncPromiseStage unguided() {
        return this.guide(null);
    }
}
