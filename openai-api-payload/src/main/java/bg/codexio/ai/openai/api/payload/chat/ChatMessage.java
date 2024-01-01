package bg.codexio.ai.openai.api.payload.chat;

import bg.codexio.ai.openai.api.payload.chat.response.ToolCallResponse;

import java.util.List;
import java.util.Objects;

public final class ChatMessage {
    private final String role;
    private final String content;
    private final List<ToolCallResponse> toolCalls;

    public ChatMessage(
            String role,
            String content,
            List<ToolCallResponse> toolCalls
    ) {
        this.role = role;
        this.content = content;
        this.toolCalls = toolCalls;
    }

    public String role() {
        return Objects.requireNonNullElse(
                this.role,
                "user"
        );
    }

    public String content() {
        return content;
    }

    public List<ToolCallResponse> toolCalls() {
        return toolCalls;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ChatMessage) obj;
        return Objects.equals(this.role,
                              that.role) && Objects.equals(this.content,
                                                           that.content)
                && Objects.equals(this.toolCalls,
                                  that.toolCalls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role,
                            content,
                            toolCalls);
    }

    @Override
    public String toString() {
        return "ChatMessage[" + "role=" + role + ", " + "content=" + content
                + ", " + "toolCalls=" + toolCalls + ']';
    }

}
