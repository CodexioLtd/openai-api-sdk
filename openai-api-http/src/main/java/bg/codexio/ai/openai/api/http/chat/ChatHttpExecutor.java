package bg.codexio.ai.openai.api.http.chat;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

/**
 * Implementation for Completions API
 */
public class ChatHttpExecutor
        extends DefaultOpenAIHttpExecutor<ChatMessageRequest,
        ChatMessageResponse> {

    private static final Class<ChatMessageResponse> RESPONSE_TYPE =
            ChatMessageResponse.class;
    private static final String RESOURCE_URI = "/chat/completions";

    public ChatHttpExecutor(
            OkHttpClient client,
            String baseUrl,
            ObjectMapper objectMapper
    ) {
        super(
                client,
                baseUrl,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                ChatHttpExecutor.class
        );
    }

    public ChatHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                ChatHttpExecutor.class
        );
    }
}
