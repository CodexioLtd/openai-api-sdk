package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

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

    public LogprobsStage enable() {
        return new LogprobsStage(
                this.executor,
                this.requestBuilder.shouldReturnLogprobs(true)
        );
    }

    public LogprobsStage withTop(Integer topLogprobs) {
        return new LogprobsStage(
                this.executor,
                this.requestBuilder.shouldReturnLogprobs(true)
                                   .withTopLogprobs(topLogprobs)
        );
    }

    public ManualConfigurationStage and() {
        return new ManualConfigurationStage(
                this.executor,
                this.requestBuilder
        );
    }
}