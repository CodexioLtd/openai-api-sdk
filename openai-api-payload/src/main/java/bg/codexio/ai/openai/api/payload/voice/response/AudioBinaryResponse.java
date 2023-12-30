package bg.codexio.ai.openai.api.payload.voice.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an
 * <a href="https://platform.openai.com/docs/api-reference/audiot>Audio#response</a>
 */
public record AudioBinaryResponse(
        byte[] bytes
)
        implements Mergeable<AudioBinaryResponse> {
    @Override
    public AudioBinaryResponse merge(AudioBinaryResponse other) {
        return new AudioBinaryResponse(Objects.requireNonNullElse(
                this.bytes(),
                other.bytes()
        ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioBinaryResponse that = (AudioBinaryResponse) o;
        return Arrays.equals(
                bytes,
                that.bytes
        );
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }
}
