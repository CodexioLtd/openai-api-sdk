package bg.codexio.ai.openai.api.payload.message.response;

import java.util.List;

public record ListMessagesResponse(
        String object,
        List<MessageCreationResponse> data,
        String firstId,
        String lastId,
        Boolean hasMore
)
        implements MessageResponse {

    @Override
    public ListMessagesResponse merge(MessageResponse other) {
        if (!other.getClass()
                  .isAssignableFrom(this.getClass())) {
            return this;
        }

        return null;
    }
}