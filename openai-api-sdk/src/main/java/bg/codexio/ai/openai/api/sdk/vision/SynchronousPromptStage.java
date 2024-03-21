package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.QuestionVisionRequest;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Synchronous context to choose between raw response or just
 * a simple string answer.
 */
public class SynchronousPromptStage
        extends VisionConfigurationStage
        implements RuntimeExecutor {

    private static final String DEFAULT_PROMPT = "What’s in this image?";

    SynchronousPromptStage(
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
     *
     * @param prompt user supplied prompt
     * @return raw {@link ChatMessageResponse}
     */
    public ChatMessageResponse describeRaw(String prompt) {
        return this.executor.immediate()
                            .execute(this.requestContext.withMessageOn(
                                    0,
                                    this.requestContext.messages()
                                                       .get(0)
                                                       .withContent(new QuestionVisionRequest(prompt))
                            ));
    }


    /**
     * Sends request to the OpenAI API.
     * Uses {@link #DEFAULT_PROMPT}
     *
     * @return raw {@link ChatMessageResponse}
     */
    public ChatMessageResponse describeRaw() {
        return this.executor.immediate()
                            .execute(this.requestContext.withMessageOn(
                                    0,
                                    this.requestContext.messages()
                                                       .get(0)
                                                       .withContent(new QuestionVisionRequest(DEFAULT_PROMPT))
                            ));
    }

    /**
     * Sends request to the OpenAI API.
     *
     * @param prompt user supplied prompt
     * @return string with the answer
     */
    public String describe(String prompt) {
        return this.describeRaw(prompt)
                   .choices()
                   .get(0)
                   .message()
                   .content();
    }

    /**
     * Sends request to the OpenAI API.
     * Uses {@link #DEFAULT_PROMPT}
     *
     * @return string with the answer
     */
    public String describe() {
        return this.describe(DEFAULT_PROMPT);
    }
}
