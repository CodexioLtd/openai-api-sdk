package bg.codexio.ai.openai.api.http.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.exception.HttpCallFailedException;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageCreationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MessageHttpExecutor
        extends DefaultOpenAIHttpExecutor<MessageRequest,
        MessageCreationResponse> {
    private static final Class<MessageCreationResponse> RESPONSE_TYPE =
            MessageCreationResponse.class;
    private static final String RESOURCE_URI = "/threads/%s/messages";

    public MessageHttpExecutor(
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
                MessageHttpExecutor.class
        );
    }

    public MessageHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                MessageHttpExecutor.class
        );
    }

    @Override
    @NotNull
    protected Request prepareRequestWithPathVariable(
            MessageRequest request,
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

    @Override
    protected Request prepareRequest(String... pathVariables) {
        var resourceUriWithPathVariable = String.format(
                this.resourceUri,
                (Object[]) pathVariables
        );

        return new Request.Builder().url(this.baseUrl.concat(resourceUriWithPathVariable))
                                    .get()
                                    .addHeader(
                                            "OpenAI-Beta",
                                            "assistants=v1"
                                    )
                                    .build();
    }

    @Override
    protected MessageCreationResponse toResponse(Response response)
            throws IOException {
        try {
            var body = response.body()
                               .string();
            log(
                    "Received raw response: {}",
                    body
            );

            return this.toResponse(body);
        } catch (IOException e) {
            throw new HttpCallFailedException(
                    this.baseUrl + this.resourceUri,
                    e
            );
        }
    }
}