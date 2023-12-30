package bg.codexio.ai.openai.api.payload.images.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.io.File;

public record ImageVariationRequest(
        File image,
        String prompt,
        String model,
        Integer n,
        String size,
        String responseFormat,
        String user
)
        implements ImageRequest,
                   Streamable {
    @Override
    public boolean stream() {
        return false;
    }
}
