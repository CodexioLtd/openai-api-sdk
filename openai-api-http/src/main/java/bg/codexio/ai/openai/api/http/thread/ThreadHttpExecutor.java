package bg.codexio.ai.openai.api.http.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public class ThreadHttpExecutor
        extends DefaultOpenAIHttpExecutor<CreateThreadRequest, ThreadResponse> {

    private static final Class<ThreadResponse> RESPONSE_TYPE =
            ThreadResponse.class;
    private static final String RESOURCE_URI = "/threads";

    public ThreadHttpExecutor(
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
                ThreadHttpExecutor.class
        );
    }

    public ThreadHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                ThreadHttpExecutor.class
        );
    }

    @Override
    @NotNull
    protected Request prepareRequest(CreateThreadRequest request) {
        var json = this.performRequestInitialization(
                request,
                this.resourceUri
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