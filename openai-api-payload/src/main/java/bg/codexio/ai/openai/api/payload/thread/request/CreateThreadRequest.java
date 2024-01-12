package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.MetadataUtils;
import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record CreateThreadRequest(
        List<MessageRequest> messages,
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
            List<MessageRequest> messages,
            Map<String, String> metadata
    ) {
        public Builder withMessages(List<MessageRequest> messages) {
            return new Builder(
                    messages,
                    metadata
            );
        }

        public Builder addMessage(MessageRequest message) {
            var messages = new ArrayList<>(Objects.requireNonNullElse(
                    this.messages,
                    new ArrayList<MessageRequest>()
            ));
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
            var threadMetadata = MetadataUtils.addMetadata(
                    this.metadata,
                    metadata
            );
            return this.withMetadata(threadMetadata);
        }

        public CreateThreadRequest build() {
            return new CreateThreadRequest(
                    messages,
                    metadata
            );
        }
    }
}
