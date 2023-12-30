package bg.codexio.ai.openai.api.http.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.images.request.ImageVariationRequest;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation for Create Image Variation API
 */
public class ImageVariationHttpExecutor
        extends DefaultOpenAIHttpExecutor<ImageVariationRequest,
        ImageDataResponse> {

    private static final Class<ImageDataResponse> RESPONSE_TYPE =
            ImageDataResponse.class;
    private static final String RESOURCE_URI = "/images/variations";
    private static final String MEDIA_TYPE = "image/png";

    public ImageVariationHttpExecutor(
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
                ImageVariationHttpExecutor.class
        );
    }

    public ImageVariationHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                false,
                ImageVariationHttpExecutor.class
        );
    }

    @NotNull
    @Override
    protected Request prepareRequest(ImageVariationRequest request) {
        this.reinitializeExecutionIdentification();
        this.setFormDataMimeType(MEDIA_TYPE);

        log(
                "Incoming request to {}{} with body: {}",
                this.baseUrl,
                this.resourceUri,
                request
        );

        return new Request.Builder().url(this.baseUrl + this.resourceUri)
                                    .post(this.toFormData(request))
                                    .build();
    }
}
