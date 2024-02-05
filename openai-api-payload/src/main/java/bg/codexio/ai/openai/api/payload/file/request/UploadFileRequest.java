package bg.codexio.ai.openai.api.payload.file.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.io.File;

public record UploadFileRequest(
        File file,
        String purpose
)
        implements Streamable {

    public static Builder builder() {
        return new Builder(
                null,
                null
        );
    }

    @Override
    public boolean stream() {
        return false;
    }

    public record Builder(
            File file,
            String purpose
    ) {
        public Builder withFile(File file) {
            return new Builder(
                    file,
                    purpose
            );
        }

        public Builder withPurpose(String purpose) {
            return new Builder(
                    file,
                    purpose
            );
        }

        public UploadFileRequest build() {
            return new UploadFileRequest(
                    file,
                    purpose
            );
        }
    }
}
