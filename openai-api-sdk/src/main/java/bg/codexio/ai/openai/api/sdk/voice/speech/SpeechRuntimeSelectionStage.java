package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

/**
 * Configures how the HTTP client to act.
 * Whether the requests will be sent in an immediate blocking manner
 * (synchronous)
 * or they will be sent asynchronously via promises or reactive API.
 */
public class SpeechRuntimeSelectionStage
        extends SpeechConfigurationStage
        implements RuntimeSelectionStage {

    SpeechRuntimeSelectionStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Requests will be synchronously blocking
     *
     * @return {@link SynchronousPromptStage} to prompt in blocking fashion
     */
    public SynchronousPromptStage immediate() {
        return new SynchronousPromptStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * Requests will be asynchronous with promises
     *
     * @return {@link SynchronousPromptStage} to prompt with promises
     */
    public AsyncPromptStage async() {
        return new AsyncPromptStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * Requests will be asynchronous reactive
     *
     * @return {@link SynchronousPromptStage} to prompt in reactive fashion
     */
    public ReactivePromptStage reactive() {
        return new ReactivePromptStage(
                this.executor,
                this.requestBuilder
        );
    }
}
