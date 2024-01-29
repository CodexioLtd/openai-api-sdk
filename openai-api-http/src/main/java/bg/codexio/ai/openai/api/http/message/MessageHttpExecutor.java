package bg.codexio.ai.openai.api.http.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

public class MessageHttpExecutor
        extends DefaultOpenAIHttpExecutor<MessageRequest, MessageResponse> {

    private static final Class<MessageResponse> RESPONSE_TYPE =
            MessageResponse.class;
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
}