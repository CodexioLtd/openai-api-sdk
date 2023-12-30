package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

/**
 * Configures maxTokens, N(choices) and stop.
 */
public class TokenStage
        extends ChatConfigurationStage {

    TokenStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures maxTokens
     *
     * @return self
     */
    public TokenStage max(int maxTokens) {
        return new TokenStage(
                this.executor,
                this.requestBuilder.withMaxTokens(maxTokens)
        );
    }

    /**
     * Configures n
     *
     * @return self
     */
    public TokenStage choices(int n) {
        return new TokenStage(
                this.executor,
                this.requestBuilder.withN(n)
        );

    }

    /**
     * Configures stop
     *
     * @return self
     */
    public TokenStage stopAt(String... tokens) {
        return new TokenStage(
                this.executor,
                this.requestBuilder.withStop(tokens)
        );
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
}
