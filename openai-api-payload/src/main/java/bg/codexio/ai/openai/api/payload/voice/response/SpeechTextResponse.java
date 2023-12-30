package bg.codexio.ai.openai.api.payload.voice.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

/**
 * Represents an
 * <a href="https://platform.openai.com/docs/api-reference/audiot>Audio#response</a>
 */
public record SpeechTextResponse(
        String text
)
        implements Mergeable<SpeechTextResponse> {
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
}
