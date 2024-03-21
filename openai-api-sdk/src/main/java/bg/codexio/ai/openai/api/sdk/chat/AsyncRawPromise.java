package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;

import java.util.function.Consumer;

/**
 * Promise abstraction.
 * Accepts callbacks which will be called
 * when response is received.
 */
public class AsyncRawPromise
        extends ChatConfigurationStage {

    AsyncRawPromise(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Subscribe both to each line (whether streamed or from the whole response)
     * and to the whole response when all lines are supplied.
     *
     * @param onEachLine callback that accepts each response line as a string
     * @param onResponse callback that accepts the {@link ChatMessageResponse}
     */
    public void then(
            Consumer<String> onEachLine,
            Consumer<ChatMessageResponse> onResponse
    ) {
        this.executor.async()
                     .execute(
                             this.requestBuilder.build(),
                             onEachLine,
                             onResponse
                     );
    }

    /**
     * Subscribe to each line (whether streamed or from the whole response)
     *
     * @param onEachLine callback that accepts each response line as a string
     */
    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {
                }
        );
    }

    /**
     * Subscribe to the whole response when all lines are supplied.
     *
     * @param onResponse callback that accepts the {@link ChatMessageResponse}
     */
    public void then(Consumer<ChatMessageResponse> onResponse) {
        this.then(
                x -> {
                },
                onResponse
        );
    }
}
