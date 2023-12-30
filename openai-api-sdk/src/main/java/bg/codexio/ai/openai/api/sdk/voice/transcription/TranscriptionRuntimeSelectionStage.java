package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

/**
 * Configures how the HTTP client to act.
 * Whether the requests will be sent in an immediate blocking manner
 * (synchronous)
 * or they will be sent asynchronously via promises or reactive API.
 */
public class TranscriptionRuntimeSelectionStage
        extends TranscriptionConfigurationStage
        implements RuntimeSelectionStage {

    TranscriptionRuntimeSelectionStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
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
     * @return {@link AsyncPromptStage} to prompt with promises
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
     * @return {@link ReactivePromptStage} to prompt in reactive fashion
     */
    public ReactivePromptStage reactive() {
        return new ReactivePromptStage(
                this.executor,
                this.requestBuilder
        );
    }
}
