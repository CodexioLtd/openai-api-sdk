package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Synchronous context to choose between raw response or just
 * a simple string answer.
 */
public class ImmediateContextStage
        extends ChatConfigurationStage
        implements RuntimeExecutor {

    ImmediateContextStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sends request to the OpenAI API.
     *
     * @param questions user supplied prompt
     * @return raw {@link ChatMessageResponse}
     */
    public ChatMessageResponse askRaw(String... questions) {
        return this.executor.execute(this.ask(Arrays.stream(questions)
                .map(q -> new ChatMessage(
                        "user",
                        q,
                        null
                ))
                .collect(Collectors.toCollection(LinkedList::new))).requestBuilder.build());
    }

    /**
     * Sends request to the OpenAI API.
     *
     * @param questions user supplied prompt
     * @return string with the answer
     */
    public String ask(String... questions) {
        return Optional
                .ofNullable(this.getMessage(questions))
                .map(ChatMessage::content)
                .orElseGet(() -> this.getMessageFromToolCallResponse(questions));
    }

    private ImmediateContextStage ask(Queue<ChatMessage> questions) {
        if (questions.isEmpty()) {
            return this;
        }

        return new ImmediateContextStage(
                this.executor,
                this.requestBuilder.addMessage(questions.poll())
        ).ask(questions);
    }

    private String getMessageFromToolCallResponse(String... questions) {
        return this.getMessage(questions)
                .toolCalls()
                .get(0)
                .function()
                .arguments();
    }

    private ChatMessage getMessage(String... questions) {
        return this.askRaw(questions)
                .choices()
                .get(0)
                .message();
    }
}
