package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

public record ChatUsageResponse(
        int promptTokens,
        int completionTokens,
        int totalTokens
)
        implements Mergeable<ChatUsageResponse> {

    public static ChatUsageResponse empty() {
        return new ChatUsageResponse(
                0,
                0,
                0
        );
    }

    @Override
    public ChatUsageResponse merge(ChatUsageResponse other) {
        return new ChatUsageResponse(
                this.promptTokens() + other.promptTokens(),
                this.completionTokens() + other.completionTokens(),
                this.totalTokens() + other.totalTokens()
        );
    }
}
