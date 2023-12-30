package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Registers text input optionally
 */
public class ReactivePromptStage
        extends TranslationConfigurationStage
        implements RuntimeExecutor {

    ReactivePromptStage(
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
     * Notifies the {@link ReactiveExecution#lines()} and
     * {@link ReactiveExecution#response()}
     * when parts (and the whole) of the response is received.
     * <font color='red'>Do not</font> subscribe simultaneously for both,
     * as you may experience bugs with multiplexing.
     *
     * @return {@link ReactiveExecution <SpeechTextResponse>}
     */
    public ReactiveExecution<SpeechTextResponse> guide(String prompt) {
        return this.executor.executeReactive(this.requestBuilder.withPrompt(prompt)
                                                                .build());
    }

    /**
     * Sends request to OpenAI API without additional prompt
     * Notifies the {@link ReactiveExecution#lines()} and
     * {@link ReactiveExecution#response()}
     * when parts (and the whole) of the response is received.
     * <font color='red'>Do not</font> subscribe simultaneously for both,
     * as you may experience bugs with multiplexing.
     *
     * @return {@link ReactiveExecution<SpeechTextResponse>}
     */
    public ReactiveExecution<SpeechTextResponse> unguided() {
        return this.guide(null);
    }
}
