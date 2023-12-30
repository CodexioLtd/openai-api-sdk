package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

/**
 * Base for all Chat stages
 */
public abstract class ChatConfigurationStage {

    protected final ChatHttpExecutor executor;
    protected final ChatMessageRequest.Builder requestBuilder;

    ChatConfigurationStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}
