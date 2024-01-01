package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.List;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/chat/object>Chat completion object</a>
 */
public final class ChatMessageResponse
        implements Mergeable<ChatMessageResponse> {
    private final String id;
    private final String object;
    private final long created;
    private final String model;
    private final ChatUsageResponse usage;
    private final List<ChatChoiceResponse> choices;

    /**
     *
     */
    public ChatMessageResponse(
            String id,
            String object,
            long created,
            String model,
            ChatUsageResponse usage,
            List<ChatChoiceResponse> choices
    ) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.usage = usage;
        this.choices = choices;
    }

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

    public String id() {
        return id;
    }

    public String object() {
        return object;
    }

    public long created() {
        return created;
    }

    public String model() {
        return model;
    }

    public ChatUsageResponse usage() {
        return usage;
    }

    public List<ChatChoiceResponse> choices() {
        return choices;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ChatMessageResponse) obj;
        return Objects.equals(this.id,
                              that.id) && Objects.equals(this.object,
                                                         that.object)
                && this.created == that.created && Objects.equals(this.model,
                                                                  that.model)
                && Objects.equals(this.usage,
                                  that.usage) && Objects.equals(this.choices,
                                                                that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                            object,
                            created,
                            model,
                            usage,
                            choices);
    }

    @Override
    public String toString() {
        return "ChatMessageResponse[" + "id=" + id + ", " + "object=" + object
                + ", " + "created=" + created + ", " + "model=" + model + ", "
                + "usage=" + usage + ", " + "choices=" + choices + ']';
    }


}
