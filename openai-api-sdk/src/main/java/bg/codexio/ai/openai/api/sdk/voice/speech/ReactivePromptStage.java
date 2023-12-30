package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Registers text input
 */
public class ReactivePromptStage
        extends SpeechConfigurationStage
        implements RuntimeExecutor {

    ReactivePromptStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * @param text input to be registered
     * @return {@link ReactiveDownloadStage} to call the API
     */
    public ReactiveDownloadStage dictate(String text) {
        return new ReactiveDownloadStage(
                this.executor,
                this.requestBuilder.withInput(text)
        );
    }
}
