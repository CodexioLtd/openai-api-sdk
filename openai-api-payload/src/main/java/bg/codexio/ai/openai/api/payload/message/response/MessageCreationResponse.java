package bg.codexio.ai.openai.api.payload.message.response;

import bg.codexio.ai.openai.api.payload.message.content.MessageContent;

import java.util.List;

public record MessageCreationResponse(
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
        implements MessageResponse {

    @Override
    public MessageResponse merge(MessageResponse other) {
        if (!other.getClass()
                  .isAssignableFrom(this.getClass())) {
            return this;
        }

        return null;
    }
}
