package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Sends blocking requests to OpenAI API
 * with given user supplied prompt
 */
public class SynchronousPromptStage
        extends TranslationConfigurationStage
        implements RuntimeExecutor {

    SynchronousPromptStage(
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
     * @return {@link SpeechTextResponse} the response from the API in the
     * configured format
     */
    public SpeechTextResponse guide(String prompt) {
        return this.executor.execute(this.requestBuilder.withPrompt(prompt)
                                                        .build());
    }

    /**
     * Sends request to OpenAI API without additional prompt
     *
     * @return {@link SpeechTextResponse} the response from the API in the
     * configured format
     */
    public SpeechTextResponse unguided() {
        return this.guide(null);
    }
}
