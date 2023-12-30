package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Registers user input optionally
 */
public class AsyncPromptStage
        extends TranscriptionConfigurationStage
        implements RuntimeExecutor {

    AsyncPromptStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Guides the transcription with additional prompt
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
     * @return {@link AsyncPromptStage} to execute the request with callbacks
     */
    public AsyncPromiseStage unguided() {
        return this.guide(null);
    }
}
