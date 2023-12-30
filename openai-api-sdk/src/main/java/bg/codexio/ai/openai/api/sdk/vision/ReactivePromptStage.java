package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.QuestionVisionRequest;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Sends calls to OpenAI API
 * in a reactive fashion
 */
public class ReactivePromptStage
        extends VisionConfigurationStage
        implements RuntimeExecutor {

    private static final String DEFAULT_PROMPT = "What’s in this image?";

    ReactivePromptStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    /**
     * Sends async request to OpenAI API.
     * Notifies the {@link ReactiveExecution#lines()} and
     * {@link ReactiveExecution#response()}
     * when parts (and the whole) of the response is received.
     * <font color='red'>Do not</font> subscribe simultaneously for both,
     * as you may experience bugs with multiplexing.
     *
     * @param prompt user supplied prompt
     * @return {@link ReactiveExecution<ChatMessageResponse>}
     */
    public ReactiveExecution<ChatMessageResponse> describe(String prompt) {
        return this.executor.executeReactive(this.requestContext.withMessageOn(
                0,
                this.requestContext.messages()
                                   .get(0)
                                   .withContent(new QuestionVisionRequest(prompt))
        ));
    }

    /**
     * Sends async request to OpenAI API.
     * Notifies the {@link ReactiveExecution#lines()} and
     * {@link ReactiveExecution#response()}
     * when parts (and the whole) of the response is received.
     * <font color='red'>Do not</font> subscribe simultaneously for both,
     * as you may experience bugs with multiplexing.
     * {@link #DEFAULT_PROMPT} is supplied.
     *
     * @return {@link ReactiveExecution<ChatMessageResponse>}
     */
    public ReactiveExecution<ChatMessageResponse> describe() {
        return this.describe(DEFAULT_PROMPT);
    }
}
