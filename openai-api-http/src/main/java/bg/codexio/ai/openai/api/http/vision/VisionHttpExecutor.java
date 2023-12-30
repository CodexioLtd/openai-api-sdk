package bg.codexio.ai.openai.api.http.vision;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

/**
 * Implementation for Vision API
 */
public class VisionHttpExecutor
        extends DefaultOpenAIHttpExecutor<VisionRequest, ChatMessageResponse> {

    private static final Class<ChatMessageResponse> RESPONSE_TYPE =
            ChatMessageResponse.class;
    private static final String RESOURCE_URI = "/chat/completions";

    public VisionHttpExecutor(
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
                VisionHttpExecutor.class
        );
    }

    public VisionHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                VisionHttpExecutor.class
        );
    }
}
