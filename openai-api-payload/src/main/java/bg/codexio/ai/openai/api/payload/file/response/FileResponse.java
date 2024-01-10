package bg.codexio.ai.openai.api.payload.file.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

public record FileResponse(
        String id,
        String object,
        Integer bytes,
        Integer createdAt,
        String filename,
        String purpose
)
        implements Mergeable<FileResponse> {
    @Override
    public FileResponse merge(FileResponse other) {
        if (other == null) {
            return this;
        }
        return new FileResponse(
                this.id,
                this.object,
                this.bytes,
                this.createdAt,
                this.filename,
                this.purpose
        );
    }
}