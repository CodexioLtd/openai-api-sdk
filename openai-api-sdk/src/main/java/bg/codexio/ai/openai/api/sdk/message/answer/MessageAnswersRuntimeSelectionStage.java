package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class MessageAnswersRuntimeSelectionStage
        extends MessageAnswersConfigurationStage
        implements RuntimeSelectionStage {


    public MessageAnswersRuntimeSelectionStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(
                httpExecutor,
                threadId
        );
    }

    @Override
    public MessageAnswersImmediateContextStage immediate() {
        return new MessageAnswersImmediateContextStage(
                this.httpExecutor,
                this.threadId
        );
    }

    @Override
    public MessageAnswersAsyncContextStage async() {
        return new MessageAnswersAsyncContextStage(
                this.httpExecutor,
                this.threadId
        );
    }

    @Override
    public MessageAnswersReactiveContextStage reactive() {
        return new MessageAnswersReactiveContextStage(
                this.httpExecutor,
                this.threadId
        );
    }
}
