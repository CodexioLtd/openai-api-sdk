package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class MessageAnswersAsyncContextStage
        extends MessageAnswersConfigurationStage
        implements RuntimeExecutor {
    protected MessageAnswersAsyncContextStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(
                httpExecutor,
                threadId
        );
    }

    public MessageAnswersAsyncPromiseStage answers() {
        return new MessageAnswersAsyncPromiseStage(
                this.httpExecutor,
                this.threadId
        );
    }
}
