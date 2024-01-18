package bg.codexio.ai.openai.api.payload.message.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.content.MessageContent;

import java.util.List;
import java.util.Objects;

public record MessageResponse(
        String id,
        String object,
        Integer createdAt,
        String threadId,
        String role,
        List<MessageContent> content,
        String assistantId,
        String runId,
        List<String> fileIds
)
        implements Mergeable<MessageResponse> {

    @Override
    public MessageResponse merge(MessageResponse other) {
        if (Objects.isNull(other)) {
            return this;
        }

        return null;
    }
}
