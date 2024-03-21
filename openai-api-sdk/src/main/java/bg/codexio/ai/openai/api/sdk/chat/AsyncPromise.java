package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

import java.util.function.Consumer;

/**
 * Promise abstraction.
 * Accepts a callback which will be called
 * when response is received.
 */
public class AsyncPromise
        extends ChatConfigurationStage {

    AsyncPromise(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Subscribe to the answer
     *
     * @param onResponse a callback that receives the string answer
     */
    public void then(Consumer<String> onResponse) {
        this.executor.async()
                     .execute(
                             this.requestBuilder.build(),
                             x -> {
                             },
                             r -> onResponse.accept(r.choices()
                                                     .get(0)
                                                     .message()
                                                     .content())
                     );
    }
}
