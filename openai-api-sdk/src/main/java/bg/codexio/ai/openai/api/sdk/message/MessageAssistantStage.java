package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.run.RunnableAdvancedConfigurationStage;
import bg.codexio.ai.openai.api.sdk.run.Runnables;

public class MessageAssistantStage<O extends Mergeable<O>>
        extends MessageConfigurationStage<O> {
    MessageAssistantStage(
            DefaultOpenAIHttpExecutor<MessageRequest, O> httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public RunnableAdvancedConfigurationStage assist(String assistantId) {
        return Runnables.defaults(this.threadId)
                        .and()
                        .deepConfigure(assistantId);
    }

    public RunnableAdvancedConfigurationStage assist(AssistantResponse assistantResponse) {
        return Runnables.defaults(this.threadId)
                        .and()
                        .deepConfigure(assistantResponse);
    }
}
