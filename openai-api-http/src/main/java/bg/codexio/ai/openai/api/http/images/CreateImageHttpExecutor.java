package bg.codexio.ai.openai.api.http.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

/**
 * Implementation for Create Image API
 */
public class CreateImageHttpExecutor
        extends DefaultOpenAIHttpExecutor<CreateImageRequest,
        ImageDataResponse> {

    private static final Class<ImageDataResponse> RESPONSE_TYPE =
            ImageDataResponse.class;
    private static final String RESOURCE_URI = "/images/generations";

    public CreateImageHttpExecutor(
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
                false,
                CreateImageHttpExecutor.class
        );
    }

    public CreateImageHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                false,
                CreateImageHttpExecutor.class
        );
    }

}
