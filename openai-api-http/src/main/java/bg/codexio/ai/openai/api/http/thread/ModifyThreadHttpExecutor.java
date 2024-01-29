package bg.codexio.ai.openai.api.http.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public class ModifyThreadHttpExecutor
        extends DefaultOpenAIHttpExecutor<ThreadModificationRequest,
        ThreadResponse> {

    private static final Class<ThreadResponse> RESPONSE_TYPE =
            ThreadResponse.class;
    private static final String RESOURCE_URI = "/threads/%s";

    public ModifyThreadHttpExecutor(
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
                ModifyThreadHttpExecutor.class
        );
    }

    public ModifyThreadHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                ModifyThreadHttpExecutor.class
        );
    }

    @Override
    @NotNull
    protected Request prepareRequestWithPathVariable(
            ThreadModificationRequest request,
            String pathVariable
    ) {
        var resourceUriWithPathVariable = String.format(
                this.resourceUri,
                pathVariable
        );
        var json = this.performRequestInitialization(
                request,
                resourceUriWithPathVariable
        );

        return new Request.Builder().url(this.baseUrl.concat(resourceUriWithPathVariable))
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
