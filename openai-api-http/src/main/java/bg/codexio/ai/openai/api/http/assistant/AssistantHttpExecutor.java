package bg.codexio.ai.openai.api.http.assistant;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

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

    @Override
    @NotNull
    protected Request prepareRequest(AssistantRequest request) {
        var json = this.performRequestInitialization(
                request,
                this.resourceUri
        );

        return new Request.Builder().url(this.baseUrl.concat(this.resourceUri))
                                    .post(RequestBody.create(
                                            json,
                                            DEFAULT_MEDIA_TYPE
                                    ))
                                    .addHeader(
                                            "OpenAI-Beta",
                                            "assistants=v1"
                                    )
                                    .build();
    }
}