package bg.codexio.ai.openai.api.payload.run.request;

import bg.codexio.ai.openai.api.payload.MetadataUtils;
import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.Map;

public record RunnableRequest(
        String assistantId,
        String model,
        String additionalInstructions,
        Map<String, String> metadata
)
        implements Streamable {
    public static Builder builder() {
        return new Builder(
                "",
                null,
                null,
                null
        );
    }

    @Override
    public boolean stream() {
        return false;
    }

    public record Builder(
            String assistantId,
            String model,
            String additionalInstructions,
            Map<String, String> metadata
    ) {
        public Builder withAssistantId(String assistantId) {
            return new Builder(
                    assistantId,
                    model,
                    additionalInstructions,
                    metadata
            );
        }

        public Builder withModel(String model) {
            return new Builder(
                    assistantId,
                    model,
                    additionalInstructions,
                    metadata
            );
        }

        public Builder withAdditionalInstructions(String additionalInstructions) {
            return new Builder(
                    assistantId,
                    model,
                    additionalInstructions,
                    metadata
            );
        }

        public Builder withMetadata(Map<String, String> metadata) {
            return new Builder(
                    assistantId,
                    model,
                    additionalInstructions,
                    metadata
            );
        }

        public Builder addMetadata(String... metadata) {
            var runnableMetadata = MetadataUtils.addMetadata(
                    this.metadata,
                    metadata
            );
            return this.withMetadata(runnableMetadata);
        }

        public RunnableRequest build() {
            return new RunnableRequest(
                    assistantId,
                    model,
                    additionalInstructions,
                    metadata
            );
        }
    }
}
