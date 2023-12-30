package bg.codexio.ai.openai.api.payload.vision.request;

public record QuestionVisionRequest(String text)
        implements VisionMessage {
    @Override
    public String getType() {
        return "text";
    }
}
