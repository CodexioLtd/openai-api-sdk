package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Registers text input
 */
public class AsyncPromptStage
        extends SpeechConfigurationStage
        implements RuntimeExecutor {

    AsyncPromptStage(
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
     * @return {@link AsyncDownloadStage} to configure target folder
     */
    public AsyncDownloadStage dictate(String text) {
        return new AsyncDownloadStage(
                this.executor,
                this.requestBuilder.withInput(text)
        );
    }
}
