package bg.codexio.ai.openai.api.http.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation for Create Image Edit API
 */
public class EditImageHttpExecutor
        extends DefaultOpenAIHttpExecutor<EditImageRequest, ImageDataResponse> {

    private static final Class<ImageDataResponse> RESPONSE_TYPE =
            ImageDataResponse.class;
    private static final String RESOURCE_URI = "/images/edits";
    private static final String MEDIA_TYPE = "image/png";

    public EditImageHttpExecutor(
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
                EditImageHttpExecutor.class
        );
    }

    public EditImageHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                false,
                EditImageHttpExecutor.class
        );
    }

    @NotNull
    @Override
    protected Request prepareRequest(EditImageRequest request) {
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

    @Override
    protected void setMultipartBoundary(String boundary) {
        super.setMultipartBoundary(boundary);
    }
}
