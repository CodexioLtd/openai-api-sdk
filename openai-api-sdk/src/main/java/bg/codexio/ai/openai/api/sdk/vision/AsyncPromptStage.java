package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.QuestionVisionRequest;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Sends calls to OpenAI API
 * with the already defined
 * callbacks, and possibly
 * with prompt.
 */
public class AsyncPromptStage
        extends VisionConfigurationStage
        implements RuntimeExecutor {

    private static final String DEFAULT_PROMPT = "What’s in this image?";

    AsyncPromptStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    /**
     * Sends request to the OpenAI API.
     * Response will be received in the supplied callbacks from previous stage.
     *
     * @param prompt user supplied prompt
     * @return {@link AsyncPromise}
     */
    public AsyncPromise describe(String prompt) {
        return new AsyncPromise(
                this.executor,
                this.requestContext.withMessageOn(
                        0,
                        this.requestContext.messages()
                                           .get(0)
                                           .withContent(new QuestionVisionRequest(prompt))
                )
        );
    }

    /**
     * Sends request to the OpenAI API.
     * Uses {@link #DEFAULT_PROMPT}
     * Response will be received in the supplied callbacks from previous stage.
     *
     * @return {@link AsyncPromise}
     */
    public AsyncPromise describe() {
        return this.describe(DEFAULT_PROMPT);
    }
}
