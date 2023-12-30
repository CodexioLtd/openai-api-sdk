package bg.codexio.ai.openai.api.payload.images.request;

import bg.codexio.ai.openai.api.payload.Streamable;

public record CreateImageRequest(
        String prompt,
        String model,
        Integer n,
        String quality,
        String responseFormat,
        String size,
        String style,
        String user
)
        implements Streamable,
                   ImageRequest {
    @Override
    public boolean stream() {
        return false;
    }
}
