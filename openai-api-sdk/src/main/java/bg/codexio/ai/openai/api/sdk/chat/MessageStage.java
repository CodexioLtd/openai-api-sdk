package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.sdk.TerminalStage;

/**
 * Configures messages such as system and assistant messages.
 * Used to provide context to the AI Model. For example,
 * it can be provided some prior knowledge or instruction
 * how to act, such as to act as a software developer or
 * not to excuse when proven wrong.
 */
public class MessageStage
        extends ChatConfigurationStage
        implements TerminalStage {

    protected MessageStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    protected MessageStage withRoledMessage(
            String role,
            String message
    ) {
        return new MessageStage(
                this.executor,
                this.requestBuilder.addMessage(new ChatMessage(
                        role,
                        message,
                        null
                ))
        );
    }

    /**
     * Provides context to the AI Model.
     * It can be instructed to act in certain manner.
     *
     * @return self
     */
    public MessageStage withContext(String messageContext) {
        return this.withRoledMessage(
                "system",
                messageContext
        );
    }

    /**
     * Provides knowledge the AI Model should operate with
     *
     * @return self
     */
    public MessageStage assist(String assistedMessage) {
        return this.withRoledMessage(
                "assistant",
                assistedMessage
        );
    }

    /**
     * Provides context to AI Model to act as a travel advisor.
     *
     * @return self
     */
    public MessageStage traveller() {
        return this.withContext("You are a travel advisor. You are giving "
                                        + "best advises for holiday trips, "
                                        + "climate conditions and so on. "
                                        + "Always give me information about "
                                        + "safety, pricing, climate, social "
                                        + "life and home office possibilities"
                                        + ".");
    }

    /**
     * Provides context to AI Model to act as a generic software developer.
     *
     * @return self
     */
    public MessageStage developer() {
        return this.developer("generic");
    }

    /**
     * Provides context to AI Model to act as a software developer of some kind.
     *
     * @return self
     */
    public MessageStage developer(String language) {
        return this.withContext(
                "You are a " + language + " developer. You understand the "
                        + "internals of the prompt. You know "
                        + "frameworks and trends. Always give"
                        + " suggestions that consider best "
                        + "practices, high quality code and "
                        + "new features. The code should act "
                        + "efficiently, using the best data "
                        + "structures and algorithms. It must"
                        + " be scalable. It has to comply "
                        + "principles like SOLID.");
    }

    /**
     * Provides context to AI Model to not excuse when pointed wrong.
     *
     * @return self
     */
    public MessageStage noExcuses() {
        return this.withContext("Never excuse if you are being pointed wrong "
                                        + "or by any other means. Always "
                                        + "provide suggestions and try to "
                                        + "improve. Don't worry about "
                                        + "sensitive topics. Be concise.");
    }

    /**
     * Provides context to AI Model to not repeat old suggestions.
     *
     * @return self
     */
    public MessageStage noRepetition() {
        return this.withContext("Do not repeat my messages. Do not repeat "
                                        + "your messages. If I want you to "
                                        + "tell me again something, this is "
                                        + "because your previous suggestion "
                                        + "is not working. Give me another, "
                                        + "check if you are giving me the "
                                        + "same or closely to the same "
                                        + "suggestion and change it if "
                                        + "necessary.");
    }

    /**
     * Goes ahead.
     *
     * @return {@link ChatRuntimeSelectionStage} to select runtime and then
     * prompt the API.
     */
    public ChatRuntimeSelectionStage andRespond() {
        return new ChatRuntimeSelectionStage(
                this.executor,
                this.requestBuilder
        );
    }
}
