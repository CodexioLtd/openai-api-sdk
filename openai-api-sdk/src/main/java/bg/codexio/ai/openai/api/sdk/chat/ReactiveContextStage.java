package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Reactive context to choose between raw response or just
 * a simple string answer.
 * Strongly recommended to use this only if a true
 * reactive runtime is present, such as
 * <a href="https://github.com/reactor/reactor-netty">Reactor Netty</a>.
 */
public class ReactiveContextStage
        extends ChatConfigurationStage
        implements RuntimeExecutor {

    ReactiveContextStage(
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
     * Notifies the {@link ReactiveExecution#lines()} and
     * {@link ReactiveExecution#response()}
     * when parts (and the whole) of the response is received.
     * <font color='red'>Do not</font> subscribe simultaneously for both,
     * as you may experience bugs with multiplexing.
     *
     * @param questions user supplied prompt
     * @return {@link ReactiveExecution<ChatMessageResponse>}
     */
    public ReactiveExecution<ChatMessageResponse> askRaw(String... questions) {
        return new ReactiveContextStage(
                this.executor,
                this.ask(Arrays.stream(questions)
                               .map(q -> new ChatMessage(
                                       "user",
                                       q,
                                       null
                               ))
                               .collect(Collectors.toCollection(LinkedList::new))).requestBuilder
        ).execute();
    }

    /**
     * Sends async request to OpenAI API.
     * Notifies the {@link ReactiveExecution#response()}
     * the whole response is received. Maps it only to the
     * string answer.
     *
     * @param questions user supplied prompt
     * @return {@link Mono<String>} holding the answer as a string
     */
    public Mono<String> ask(String... questions) {
        return this.askRaw(questions)
                   .response()
                   .map(r -> r.choices()
                              .get(0)
                              .message()
                              .content());
    }

    private ReactiveContextStage ask(Queue<ChatMessage> questions) {
        if (questions.isEmpty()) {
            return this;
        }

        return new ReactiveContextStage(
                this.executor,
                this.requestBuilder.addMessage(questions.poll())
        ).ask(questions);
    }

    private ReactiveExecution<ChatMessageResponse> execute() {
        return this.executor.reactive()
                            .execute(this.requestBuilder.build());
    }
}
