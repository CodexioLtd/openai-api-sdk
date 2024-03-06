package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.sdk.message.DefaultMessageConfigurationStage;

public class MessageAnswersConfigurationStage
        extends DefaultMessageConfigurationStage {

    protected final RetrieveListMessagesHttpExecutor httpExecutor;

    protected MessageAnswersConfigurationStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(threadId);
        this.httpExecutor = httpExecutor;
    }
}
