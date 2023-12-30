package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Adds user input and proceeds
 * to blocking execution
 */
public class SynchronousPromptStage
        extends SpeechConfigurationStage
        implements RuntimeExecutor {

    SynchronousPromptStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * @param text to prompt
     * @return {@link SynchronousDownloadStage}
     */
    public SynchronousDownloadStage dictate(String text) {
        return new SynchronousDownloadStage(
                this.executor,
                this.requestBuilder.withInput(text)
        );
    }
}
