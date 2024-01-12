package bg.codexio.ai.openai.api.payload.message.request;

import bg.codexio.ai.openai.api.payload.MetadataUtils;
import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record MessageRequest(
        String role,
        String content,
        List<String> fileIds,
        Map<String, String> metadata
)
        implements Streamable {

    public static Builder builder() {
        return new Builder(
                "user",
                "",
                null,
                null
        );
    }

    @Override
    public boolean stream() {
        return false;
    }

    public record Builder(
            String role,
            String content,
            List<String> fileIds,
            Map<String, String> metadata
    ) {
        public Builder withRole(String role) {
            return new Builder(
                    role,
                    content,
                    fileIds,
                    metadata
            );
        }

        public Builder withContent(String content) {
            return new Builder(
                    role,
                    content,
                    fileIds,
                    metadata
            );
        }

        public Builder withFileIds(List<String> fileIds) {
            return new Builder(
                    role,
                    content,
                    fileIds,
                    metadata
            );
        }

        public Builder addFileIDs(String fileId) {
            var fileIds = new ArrayList<>(Objects.requireNonNullElse(
                    this.fileIds,
                    new ArrayList<>()
            ));
            fileIds.add(fileId);

            return this.withFileIds(fileIds);
        }

        public Builder withMetadata(Map<String, String> metadata) {
            return new Builder(
                    role,
                    content,
                    fileIds,
                    metadata
            );
        }

        public Builder addMetadata(String... metadata) {
            var messageMetadata = MetadataUtils.addMetadata(
                    this.metadata,
                    metadata
            );

            return this.withMetadata(messageMetadata);
        }

        public MessageRequest build() {
            return new MessageRequest(
                    role,
                    content,
                    fileIds,
                    metadata
            );
        }
    }
}