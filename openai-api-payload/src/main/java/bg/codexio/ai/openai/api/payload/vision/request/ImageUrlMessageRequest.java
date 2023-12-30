package bg.codexio.ai.openai.api.payload.vision.request;

public record ImageUrlMessageRequest(
        ImageUrlRequest imageUrl
)
        implements VisionMessage {
    @Override
    public String getType() {
        return "image_url";
    }
}
