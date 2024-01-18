package bg.codexio.ai.openai.api.payload.message.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.List;
import java.util.Objects;

public record ListMessagesResponse(
        String object,
        List<MessageResponse> data,
        String firstId,
        String lastId,
        Boolean hasMore
)
        implements Mergeable<ListMessagesResponse> {

    @Override
    public ListMessagesResponse merge(ListMessagesResponse other) {
        if (Objects.isNull(other)) {
            return this;
        }

        return null;
    }
}