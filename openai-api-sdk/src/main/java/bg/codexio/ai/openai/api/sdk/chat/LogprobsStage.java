package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.sdk.chat.exception.TopLogprobsOutOfRangeException;

import java.util.Optional;

public class LogprobsStage
        extends ChatConfigurationStage {
    LogprobsStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Enables chat to return log probabilities
     *
     * @return self
     */
    public LogprobsStage enable() {
        return new LogprobsStage(
                this.executor,
                this.requestBuilder.shouldReturnLogprobs(true)
        );
    }

    /**
     * Configures the number of tokens to return at each token position, each
     * with an associated log probability.
     *
     * @param topLogprobs {@link Integer} value between 0 and 5
     * @return self
     * @throws TopLogprobsOutOfRangeException if the provided value is out of
     * the valid range
     */
    public LogprobsStage withTop(Integer topLogprobs) {
        return Optional.ofNullable(topLogprobs)
                       .filter(this::isTopLogprobsInRange)
                       .map(this::setTop)
                       .orElseThrow(TopLogprobsOutOfRangeException::new);
    }

    /**
     * Go back
     *
     * @return {@link ManualConfigurationStage}
     */
    public ManualConfigurationStage and() {
        return new ManualConfigurationStage(
                this.executor,
                this.requestBuilder
        );
    }

    private LogprobsStage setTop(Integer topLogprobs) {
        return new LogprobsStage(
                this.executor,
                this.requestBuilder.shouldReturnLogprobs(true)
                                   .withTopLogprobs(topLogprobs)
        );
    }

    private boolean isTopLogprobsInRange(Integer topLogprobs) {
        return topLogprobs >= 0 && topLogprobs <= 5;
    }
}