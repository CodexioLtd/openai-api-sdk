package bg.codexio.ai.openai.api.payload.voice.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an
 * <a href="https://platform.openai.com/docs/api-reference/audiot>Audio#response</a>
 */
public final class AudioBinaryResponse
        implements Mergeable<AudioBinaryResponse> {
    private final byte[] bytes;

    /**
     *
     */
    public AudioBinaryResponse(
            byte[] bytes
    ) {
        this.bytes = bytes;
    }

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

    public byte[] bytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return "AudioBinaryResponse[" + "bytes=" + bytes + ']';
    }

}
