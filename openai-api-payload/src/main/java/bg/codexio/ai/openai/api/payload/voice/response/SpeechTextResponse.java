package bg.codexio.ai.openai.api.payload.voice.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

/**
 * Represents an
 * <a href="https://platform.openai.com/docs/api-reference/audiot>Audio#response</a>
 */
public final class SpeechTextResponse
        implements Mergeable<SpeechTextResponse> {
    private final String text;

    /**
     *
     */
    public SpeechTextResponse(
            String text
    ) {
        this.text = text;
    }

    @Override
    public SpeechTextResponse merge(SpeechTextResponse other) {
        return new SpeechTextResponse(Objects.requireNonNullElse(
                this.text(),
                ""
        ) + Objects.requireNonNullElse(
                other.text(),
                ""
        ));
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
        var that = (SpeechTextResponse) obj;
        return Objects.equals(this.text,
                              that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "SpeechTextResponse[" + "text=" + text + ']';
    }

}
