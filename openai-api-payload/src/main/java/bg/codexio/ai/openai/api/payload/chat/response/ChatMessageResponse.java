package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.List;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/chat/object>Chat completion object</a>
 */
public record ChatMessageResponse(
        String id,
        String object,
        long created,
        String model,
        ChatUsageResponse usage,
        List<ChatChoiceResponse> choices
)
        implements Mergeable<ChatMessageResponse> {

    @Override
    public ChatMessageResponse merge(ChatMessageResponse other) {
        if (other == null) {
            return this;
        }

        var thisUsage = Objects.requireNonNullElse(
                this.usage(),
                ChatUsageResponse.empty()
        );
        var otherUsage = Objects.requireNonNullElse(
                other.usage(),
                ChatUsageResponse.empty()
        );

        return new ChatMessageResponse(
                this.id(),
                this.object(),
                Math.max(
                        this.created(),
                        other.created()
                ),
                this.model(),
                thisUsage.merge(otherUsage),
                Mergeable.join(
                        this.choices,
                        other.choices,
                        c -> c.delta() != null || c.message() != null,
                        c -> c.delta() != null
                             ? c.delta()
                                .role()
                             : c.message()
                                .role(),
                        ChatChoiceResponse::empty,
                        ChatChoiceResponse::merge,
                        x -> true
                )
        );
    }

}
