package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

/**
 * Configures tokens, tools and accuracy/
 */
public class ManualConfigurationStage
        extends ChatConfigurationStage {

    ManualConfigurationStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures temperature, topP and penalties.
     *
     * @return {@link AccuracyStage}
     */
    public AccuracyStage accuracy() {
        return new AccuracyStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * Configures maxTokens, stop and choices number.
     *
     * @return {@link TokenStage}
     */
    public TokenStage tokens() {
        return new TokenStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * Configures tools such as functions
     *
     * @return {@link ToolStage}
     */
    public ToolStage tools() {
        return new ToolStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * Configures logprobs and top_logprobs
     *
     * @return {@link LogprobsStage}
     */
    public LogprobsStage logprobs() {
        return new LogprobsStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * After things are configured, go ahead.
     *
     * @return {@link MessageStage}
     */
    public MessageStage done() {
        return new MessageStage(
                this.executor,
                this.requestBuilder
        );
    }
}
