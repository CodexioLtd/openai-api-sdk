package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.chat.AsyncPromise;

public class VisionRuntimeSelectionStage
        extends VisionConfigurationStage
        implements RuntimeSelectionStage {

    VisionRuntimeSelectionStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }


    /**
     * Goes further to prompt the GPT model in a synchronous fashion.
     *
     * @return {@link SynchronousPromptStage}
     */
    public SynchronousPromptStage immediate() {
        return new SynchronousPromptStage(
                this.executor,
                this.requestContext
        );
    }

    /**
     * Goes further to prompt the GPT model in an asynchronous promise-based
     * fashion.
     *
     * @return {@link AsyncPromise}
     */
    public AsyncPromptStage async() {
        return new AsyncPromptStage(
                this.executor,
                this.requestContext
        );
    }

    /**
     * Goes further to prompt the GPT model in an asynchronous reactive-based
     * fashion.
     *
     * @return {@link ReactivePromptStage}
     */
    public ReactivePromptStage reactive() {
        return new ReactivePromptStage(
                this.executor,
                this.requestContext
        );
    }

}
