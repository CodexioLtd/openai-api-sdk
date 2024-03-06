package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.run.RunnableAdvancedConfigurationStage;
import bg.codexio.ai.openai.api.sdk.run.Runnables;

public class MessageAssistantStage
        extends MessageConfigurationStage {

    MessageAssistantStage(
            MessageHttpExecutor httpExecutor,
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
        this.execute();

        return Runnables.defaults(this.threadId)
                        .and()
                        .deepConfigure(assistantId);
    }

    public RunnableAdvancedConfigurationStage assist(AssistantResponse assistantResponse) {
        this.execute();

        return Runnables.defaults(this.threadId)
                        .and()
                        .deepConfigure(assistantResponse);
    }

    private void execute() {
        this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.build(),
                this.threadId
        );
    }
}