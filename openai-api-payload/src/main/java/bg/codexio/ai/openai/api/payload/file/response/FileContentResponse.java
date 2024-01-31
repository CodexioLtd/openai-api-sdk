package bg.codexio.ai.openai.api.payload.file.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Arrays;
import java.util.Objects;

public record FileContentResponse(
        byte[] bytes
)
        implements Mergeable<FileContentResponse> {
    @Override
    public FileContentResponse merge(FileContentResponse other) {
        return new FileContentResponse(Objects.requireNonNullElse(
                other.bytes,
                this.bytes
        ));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FileContentResponse that = (FileContentResponse) object;
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