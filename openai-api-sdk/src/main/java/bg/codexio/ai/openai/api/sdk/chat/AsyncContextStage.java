package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Async context to choose between raw response or just
 * a simple string answer.
 */
public class AsyncContextStage
        extends ChatConfigurationStage
        implements RuntimeExecutor {

    AsyncContextStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sends async request to OpenAI API.
     *
     * @param questions user supplied prompt
     * @return {@link AsyncRawPromise} providing a way to subscribe to the
     * raw response
     */
    public AsyncRawPromise askRaw(String... questions) {
        return new AsyncRawPromise(
                this.executor,
                this.ask(Arrays.stream(questions)
                               .map(q -> new ChatMessage(
                                       "user",
                                       q,
                                       null
                               ))
                               .collect(Collectors.toCollection(LinkedList::new))).requestBuilder
        );
    }

    /**
     * Sends async request to OpenAI API.
     *
     * @param questions user supplied prompt
     * @return {@link AsyncPromise} providing a way to subscribe to the
     * string answer promise
     */
    public AsyncPromise ask(String... questions) {
        return new AsyncPromise(
                this.executor,
                this.ask(Arrays.stream(questions)
                               .map(q -> new ChatMessage(
                                       "user",
                                       q,
                                       null
                               ))
                               .collect(Collectors.toCollection(LinkedList::new))).requestBuilder
        );
    }

    private AsyncContextStage ask(Queue<ChatMessage> questions) {
        if (questions.isEmpty()) {
            return this;
        }

        return new AsyncContextStage(
                this.executor,
                this.requestBuilder.addMessage(questions.poll())
        ).ask(questions);
    }
}
