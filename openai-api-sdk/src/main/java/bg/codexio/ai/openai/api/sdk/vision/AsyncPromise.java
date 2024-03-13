package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

import java.util.function.Consumer;

/**
 * Async promise to choose between raw response or just
 * a simple string answer.
 */
public class AsyncPromise
        extends VisionConfigurationStage
        implements AsyncPromiseStage<ChatMessageResponse> {


    AsyncPromise(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    /**
     * Subscribe both to each line (whether streamed or from the whole response)
     * and to the whole response when all lines are supplied.
     *
     * @param onEachLine callback that accepts each response line as a string
     * @param afterAll   callback that accepts the {@link ChatMessageResponse}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<ChatMessageResponse> afterAll
    ) {
        this.executor.executeAsync(
                this.requestContext,
                onEachLine,
                afterAll
        );
    }

}
