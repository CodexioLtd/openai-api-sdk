package bg.codexio.ai.openai.api.payload.file.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

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
}