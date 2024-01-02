package bg.codexio.ai.openai.api.payload.vision.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/guides/vision/quick-start">Vision</a> request
 */
public final class VisionRequest
        implements Streamable {
    private final String model;
    private final List<MessageContentHolder> messages;
    private final Integer maxTokens;

    public VisionRequest() {
        this(
                null,
                null,
                null
        );
    }

    public VisionRequest(
            String model,
            List<MessageContentHolder> messages,
            Integer maxTokens
    ) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
    }

    public static VisionRequest empty() {
        return new VisionRequest(
                "",
                new ArrayList<>(),
                null
        );
    }

    @Override
    public boolean stream() {
        return false;
    }


    @JsonProperty
    public String model() {
        return model;
    }

    @JsonProperty
    public List<MessageContentHolder> messages() {
        return messages;
    }

    @JsonProperty
    public Integer maxTokens() {
        return maxTokens;
    }


    public VisionRequest withModel(String model) {
        return new VisionRequest(
                model,
                this.messages(),
                this.maxTokens()
        );
    }

    public VisionRequest withMessages(MessageContentHolder messageHolder) {
        var messages = new ArrayList<>(this.messages());
        messages.add(messageHolder);

        return new VisionRequest(
                this.model(),
                messages,
                this.maxTokens()
        );
    }

    public VisionRequest withMessageOn(
            int index,
            MessageContentHolder messageHolder
    ) {
        var messages = new ArrayList<>(this.messages());
        messages.set(
                index,
                messageHolder
        );

        return new VisionRequest(
                this.model(),
                messages,
                this.maxTokens()
        );
    }

    public VisionRequest withTokens(Integer maxTokens) {
        return new VisionRequest(
                this.model(),
                this.messages(),
                maxTokens
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (VisionRequest) obj;
        return Objects.equals(
                this.model,
                that.model
        ) && Objects.equals(
                this.messages,
                that.messages
        ) && Objects.equals(
                this.maxTokens,
                that.maxTokens
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                model,
                messages,
                maxTokens
        );
    }

    @Override
    public String toString() {
        return "VisionRequest[" + "model=" + model + ", " + "messages="
                + messages + ", " + "maxTokens=" + maxTokens + ']';
    }

}
