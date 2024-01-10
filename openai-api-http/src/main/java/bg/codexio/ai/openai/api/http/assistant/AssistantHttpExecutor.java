package bg.codexio.ai.openai.api.http.assistant;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

public class AssistantHttpExecutor
        extends DefaultOpenAIHttpExecutor<AssistantRequest, AssistantResponse> {
    private static final Class<AssistantResponse> RESPONSE_TYPE =
            AssistantResponse.class;
    private static final String RESOURCE_URI = "/assistants";

    public AssistantHttpExecutor(
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
                AssistantHttpExecutor.class
        );
    }

    public AssistantHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                AssistantHttpExecutor.class
        );
    }
}
