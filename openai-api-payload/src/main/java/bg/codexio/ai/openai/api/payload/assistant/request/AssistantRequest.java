package bg.codexio.ai.openai.api.payload.assistant.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.assistant.AssistantTool;

import java.util.*;

public record AssistantRequest(
        String model,
        String name,
        String description,
        String instructions,
        List<AssistantTool> tools,
        List<String> fileIds,
        Map<String, String> metadata
)
        implements Streamable {

    public static Builder builder() {
        return new Builder(
                "",
                null,
                null,
                null,
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
            String model,
            String name,
            String description,
            String instructions,
            List<AssistantTool> tools,
            List<String> fileIds,
            Map<String, String> metadata
    ) {
        public Builder withModel(String model) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder withName(String name) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder withDescription(String description) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder withInstructions(String instructions) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder withTools(List<AssistantTool> tools) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder addTools(AssistantTool assistantTool) {
            var assistantTools = new ArrayList<>(Objects.requireNonNullElse(
                    this.tools,
                    new ArrayList<>()
            ));
            assistantTools.add(assistantTool);

            return this.withTools(assistantTools);
        }

        public Builder withFileIds(List<String> fileIds) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder addFileId(String fileId) {
            var fileIds = new ArrayList<>(Objects.requireNonNullElse(
                    this.fileIds,
                    new ArrayList<>()
            ));
            fileIds.add(fileId);

            return this.withFileIds(fileIds);
        }

        public Builder withMetadata(Map<String, String> metadata) {
            return new Builder(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }

        public Builder addMetadata(String... metadata) {
            if (metadata.length % 2 != 0) {
                throw new IllegalArgumentException(
                        "Metadata needs to contain at " + "least 2 arguments.");
            }

            var map = Objects.requireNonNullElse(
                    this.metadata,
                    new HashMap<String, String>()
            );
            for (int i = 0; i < metadata.length - 1; i += 2) {
                map.put(
                        metadata[i],
                        metadata[i + 1]
                );
            }

            return this.withMetadata(map);
        }

        public AssistantRequest build() {
            return new AssistantRequest(
                    model,
                    name,
                    description,
                    instructions,
                    tools,
                    fileIds,
                    metadata
            );
        }
    }
}
