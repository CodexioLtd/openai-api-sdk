package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

import java.util.function.Consumer;

/**
 * Async promise to choose between raw response or just
 * a simple string answer.
 */
public class AsyncPromise
        extends VisionConfigurationStage {


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
     * @param afterAll   callback that accepts the {@link ChatMessageResponse}
     * @param onEachLine callback that accepts each response line as a string
     */
    public void then(
            Consumer<ChatMessageResponse> afterAll,
            Consumer<String> onEachLine
    ) {
        this.executor.executeAsync(
                this.requestContext,
                onEachLine,
                afterAll
        );
    }

    /**
     * Subscribe to each line (whether streamed or from the whole response)
     *
     * @param onEachLine callback that accepts each response line as a string
     */
    public void onEachLine(
            Consumer<String> onEachLine
    ) {
        this.then(
                x -> {
                },
                onEachLine
        );
    }

    /**
     * Subscribe to the whole response when all lines are supplied.
     *
     * @param afterAll callback that accepts the {@link ChatMessageResponse}
     */
    public void then(
            Consumer<ChatMessageResponse> afterAll
    ) {
        this.then(
                afterAll,
                x -> {
                }
        );
    }

}
