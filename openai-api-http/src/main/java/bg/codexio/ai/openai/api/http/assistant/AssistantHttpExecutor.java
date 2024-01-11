package bg.codexio.ai.openai.api.http.assistant;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public class AssistantHttpExecutor
        extends DefaultOpenAIHttpExecutor<AssistantRequest, AssistantResponse> {
    private static final Class<AssistantResponse> RESPONSE_TYPE =
            AssistantResponse.class;
    private static final String RESOURCE_URI = "/assistants";

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.get(
            "application/json");

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
        this.reinitializeExecutionIdentification();

        var json = this.toJson(request);

        log(
                "Incoming request to {}{} with body: {}",
                this.baseUrl,
                this.resourceUri,
                json
        );

        return new Request.Builder().url(this.baseUrl + this.resourceUri)
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
