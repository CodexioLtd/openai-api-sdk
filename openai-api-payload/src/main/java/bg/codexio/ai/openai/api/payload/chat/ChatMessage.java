package bg.codexio.ai.openai.api.payload.chat;

import bg.codexio.ai.openai.api.payload.chat.response.ToolCallResponse;

import java.util.List;
import java.util.Objects;

public record ChatMessage(
        String role,
        String content,
        List<ToolCallResponse> toolCalls
) {

    @Override
    public String role() {
        return Objects.requireNonNullElse(
                this.role,
                "user"
        );
    }
}
