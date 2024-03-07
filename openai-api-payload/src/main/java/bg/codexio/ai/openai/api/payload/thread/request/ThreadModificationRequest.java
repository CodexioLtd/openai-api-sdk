package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.MetadataUtils;
import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.Map;

public record ThreadModificationRequest(
        Map<String, String> metadata
)
        implements Streamable {
    @Override
    public boolean stream() {
        return false;
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public record Builder(
            Map<String, String> metadata
    ) {
        public Builder withMetadata(Map<String, String> metadata) {
            return new Builder(metadata);
        }

        public Builder addMetadata(String... metadata) {
            var threadMetadata = MetadataUtils.addMetadata(
                    this.metadata,
                    metadata
            );

            return this.withMetadata(threadMetadata);
        }

        public ThreadModificationRequest build() {
            return new ThreadModificationRequest(metadata);
        }
    }
}
