package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

public class MessageAnswersReactiveContextStage
        extends MessageAnswersConfigurationStage
        implements RuntimeExecutor {
    protected MessageAnswersReactiveContextStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(
                httpExecutor,
                threadId
        );
    }

    public Mono<MessageAnswersRetrievalTypeStage> answers() {
        return this.httpExecutor.executeReactiveWithPathVariables(this.threadId)
                                .response()
                                .map(MessageAnswersRetrievalTypeStage::new);
    }
}