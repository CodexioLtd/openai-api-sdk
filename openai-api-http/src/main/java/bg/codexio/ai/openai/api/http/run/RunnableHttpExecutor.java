package bg.codexio.ai.openai.api.http.run;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import static bg.codexio.ai.openai.api.http.CommonConstantsUtils.ASSISTANTS_HEADER_NAME;
import static bg.codexio.ai.openai.api.http.CommonConstantsUtils.ASSISTANTS_HEADER_VALUE;

public class RunnableHttpExecutor
        extends DefaultOpenAIHttpExecutor<RunnableRequest, RunnableResponse> {

    private static final Class<RunnableResponse> RESPONSE_TYPE =
            RunnableResponse.class;
    private static final String RESOURCE_URI = "/threads/%s/runs";

    private static final String RETRIEVE_RUN_RESOURCE_URI =
            "/threads/%s/runs" + "/%s";

    public RunnableHttpExecutor(
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
                RunnableHttpExecutor.class
        );
    }

    public RunnableHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                RunnableHttpExecutor.class
        );
    }

    @Override
    @NotNull
    protected Request prepareRequestWithPathVariable(
            RunnableRequest request,
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
                                            ASSISTANTS_HEADER_NAME,
                                            ASSISTANTS_HEADER_VALUE
                                    )
                                    .build();
    }

    @Override
    protected Request prepareRequestWithPathVariables(String... pathVariables) {
        var resourceUriWithPathVariable = String.format(
                RETRIEVE_RUN_RESOURCE_URI,
                (Object[]) pathVariables
        );

        return new Request.Builder().url(this.baseUrl.concat(resourceUriWithPathVariable))
                                    .get()
                                    .addHeader(
                                            ASSISTANTS_HEADER_NAME,
                                            ASSISTANTS_HEADER_VALUE
                                    )
                                    .build();
    }
}