package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

public final class ChatUsageResponse
        implements Mergeable<ChatUsageResponse> {
    private final int promptTokens;
    private final int completionTokens;
    private final int totalTokens;

    public ChatUsageResponse(
            int promptTokens,
            int completionTokens,
            int totalTokens
    ) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }

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

    public int promptTokens() {
        return promptTokens;
    }

    public int completionTokens() {
        return completionTokens;
    }

    public int totalTokens() {
        return totalTokens;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ChatUsageResponse) obj;
        return this.promptTokens == that.promptTokens
                && this.completionTokens == that.completionTokens
                && this.totalTokens == that.totalTokens;
    }

    @Override
    public int hashCode() {
        return Objects.hash(promptTokens,
                            completionTokens,
                            totalTokens);
    }

    @Override
    public String toString() {
        return "ChatUsageResponse[" + "promptTokens=" + promptTokens + ", "
                + "completionTokens=" + completionTokens + ", " + "totalTokens="
                + totalTokens + ']';
    }

}
