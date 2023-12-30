package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;

import java.util.Objects;

public record ChatChoiceResponse(
        ChatMessage message,
        ChatMessage delta,
        String finishReason,
        int index
)
        implements Mergeable<ChatChoiceResponse> {

    public static ChatChoiceResponse empty() {
        return new ChatChoiceResponse(
                null,
                null,
                null,
                0
        );
    }

    @Override
    public ChatChoiceResponse merge(ChatChoiceResponse other) {
        if (other == null) {
            return this;
        }

        var thisMessage = this.delta() != null
                          ? this.delta()
                          : this.message();
        var otherMessage = other.delta() != null
                           ? other.delta()
                           : other.message();

        if (thisMessage == null) {
            return other;
        }

        if (otherMessage == null) {
            return this;
        }

        return new ChatChoiceResponse(
                new ChatMessage(
                        thisMessage.role(),
                        Objects.requireNonNullElse(
                                thisMessage.content(),
                                ""
                        ) + Objects.requireNonNullElse(
                                otherMessage.content(),
                                ""
                        ),
                        Mergeable.join(
                                thisMessage.toolCalls(),
                                otherMessage.toolCalls(),
                                t -> t.index() != null,
                                ToolCallResponse::index,
                                ToolCallResponse::empty,
                                ToolCallResponse::merge,
                                t -> t.index() != null
                        )
                ),
                null,
                this.finishReason() != null
                ? this.finishReason()
                : other.finishReason(),
                Math.max(
                        this.index(),
                        other.index()
                )
        );
    }
}
