package bg.codexio.ai.openai.api.payload.vision.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MessageContentHolder {
    private final List<VisionMessage> content;

    public MessageContentHolder(
            List<VisionMessage> content
    ) {
        this.content = content;
    }

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

    public List<VisionMessage> content() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (MessageContentHolder) obj;
        return Objects.equals(this.content,
                              that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "MessageContentHolder[" + "content=" + content + ']';
    }

}
