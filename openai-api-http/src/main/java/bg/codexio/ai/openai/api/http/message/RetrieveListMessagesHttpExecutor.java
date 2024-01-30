package bg.codexio.ai.openai.api.http.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static bg.codexio.ai.openai.api.http.CommonConstantsUtils.ASSISTANTS_HEADER_NAME;
import static bg.codexio.ai.openai.api.http.CommonConstantsUtils.ASSISTANTS_HEADER_VALUE;

public class RetrieveListMessagesHttpExecutor
        extends DefaultOpenAIHttpExecutor<MessageRequest,
        ListMessagesResponse> {

    private static final Class<ListMessagesResponse> RESPONSE_TYPE =
            ListMessagesResponse.class;
    private static final String RESOURCE_URI = "/threads/%s/messages";

    public RetrieveListMessagesHttpExecutor(
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
                RetrieveListMessagesHttpExecutor.class
        );
    }

    public RetrieveListMessagesHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                RetrieveListMessagesHttpExecutor.class
        );
    }

    @Override
    protected Request prepareRequestWithPathVariables(String... pathVariables) {
        var resourceUriWithPathVariable = String.format(
                this.resourceUri,
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