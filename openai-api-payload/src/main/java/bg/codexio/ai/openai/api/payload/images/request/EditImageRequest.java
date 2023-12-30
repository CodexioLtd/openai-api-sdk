package bg.codexio.ai.openai.api.payload.images.request;

import java.io.File;

public record EditImageRequest(
        File image,
        String prompt,
        File mask,
        String model,
        Integer n,
        String size,
        String responseFormat,
        String user
)
        implements ImageRequest {
    @Override
    public boolean stream() {
        return false;
    }
}
