package bg.codexio.ai.openai.api.payload.vision.request;

import java.util.ArrayList;
import java.util.List;

public record MessageContentHolder(
        List<VisionMessage> content
) {
    public String getRole() {
        return "user";
    }

    public MessageContentHolder withContent(VisionMessage message) {
        var content = new ArrayList<>(this.content());
        content.add(message);

        return new MessageContentHolder(content);
    }

    public MessageContentHolder withContentOn(
            int index,
            VisionMessage message
    ) {
        var content = new ArrayList<>(this.content());
        content.set(
                index,
                message
        );

        return new MessageContentHolder(content);
    }
}
