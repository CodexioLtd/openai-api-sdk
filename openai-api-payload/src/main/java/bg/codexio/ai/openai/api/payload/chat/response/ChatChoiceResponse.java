package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ChatChoiceResponse
        implements Mergeable<ChatChoiceResponse> {
    private final ChatMessage message;
    private final ChatMessage delta;
    private final String finishReason;
    private final int index;

    public ChatChoiceResponse(
    ) {
        this(null, null, null, 1);
    }

    public ChatChoiceResponse(
            ChatMessage message,
            ChatMessage delta,
            String finishReason,
            int index
    ) {
        this.message = message;
        this.delta = delta;
        this.finishReason = finishReason;
        this.index = index;
    }

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

    @JsonProperty
    public ChatMessage message() {
        return message;
    }

    @JsonProperty
    public ChatMessage delta() {
        return delta;
    }

    @JsonProperty
    public String finishReason() {
        return finishReason;
    }

    @JsonProperty
    public int index() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ChatChoiceResponse) obj;
        return Objects.equals(this.message,
                              that.message) && Objects.equals(this.delta,
                                                              that.delta)
                && Objects.equals(this.finishReason,
                                  that.finishReason) && this.index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message,
                            delta,
                            finishReason,
                            index);
    }

    @Override
    public String toString() {
        return "ChatChoiceResponse[" + "message=" + message + ", " + "delta="
                + delta + ", " + "finishReason=" + finishReason + ", "
                + "index=" + index + ']';
    }

}
