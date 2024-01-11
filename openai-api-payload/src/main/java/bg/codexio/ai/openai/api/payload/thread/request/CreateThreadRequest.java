package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.thread.ThreadMessage;

import java.util.*;

public record CreateThreadRequest(
        List<ThreadMessage> messages,
        Map<String, String> metadata
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
            List<ThreadMessage> messages,
            Map<String, String> metadata
    ) {
        public Builder withMessages(List<ThreadMessage> messages) {
            return new Builder(
                    messages,
                    metadata
            );
        }

        public Builder addMessage(ThreadMessage message) {
            var messages = Objects.requireNonNullElse(
                    this.messages,
                    new ArrayList<ThreadMessage>()
            );
            messages.add(message);

            return this.withMessages(messages);
        }

        public Builder withMetadata(Map<String, String> metadata) {
            return new Builder(
                    messages,
                    metadata
            );
        }

        public Builder addMetadata(String... metadata) {
            if (metadata.length % 2 != 0) {
                throw new IllegalArgumentException(
                        "Metadata needs to contain at " + "least 2 arguments.");
            }

            var threadMetadataMap = Objects.requireNonNullElse(
                    this.metadata,
                    new HashMap<String, String>()
            );
            for (int i = 0; i < metadata.length - 1; i += 2) {
                threadMetadataMap.put(
                        metadata[i],
                        metadata[i + 1]
                );
            }

            return this.withMetadata(threadMetadataMap);
        }

        public CreateThreadRequest build() {
            return new CreateThreadRequest(
                    messages,
                    metadata
            );
        }
    }
}
