package bg.codexio.ai.openai.api.payload.vision.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/guides/vision/quick-start">Vision</a> request
 */
public record VisionRequest(
        String model,
        List<MessageContentHolder> messages,
        Integer maxTokens
)
        implements Streamable {

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
}
