package bg.codexio.ai.openai.api.payload.vision.request;

import java.util.Objects;

public final class QuestionVisionRequest
        implements VisionMessage {
    private final String text;

    public QuestionVisionRequest(String text) {
        this.text = text;
    }

    @Override
    public String getType() {
        return "text";
    }

    public String text() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (QuestionVisionRequest) obj;
        return Objects.equals(this.text,
                              that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "QuestionVisionRequest[" + "text=" + text + ']';
    }

}
