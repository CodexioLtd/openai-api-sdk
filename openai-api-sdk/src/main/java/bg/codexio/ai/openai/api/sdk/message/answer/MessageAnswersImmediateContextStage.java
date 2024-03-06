package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.message.exception.NonPresentThreadIdException;

import java.util.Optional;

public class MessageAnswersImmediateContextStage
        extends MessageAnswersConfigurationStage
        implements RuntimeExecutor {

    protected MessageAnswersImmediateContextStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(
                httpExecutor,
                threadId
        );
    }

    public MessageAnswersRetrievalTypeStage answers() {
        return Optional.ofNullable(this.threadId)
                       .map(this.httpExecutor::executeWithPathVariables)
                       .map(MessageAnswersRetrievalTypeStage::new)
                       .orElseThrow(NonPresentThreadIdException::new);
    }

    public MessageAnswersRetrievalTypeStage answers(ThreadResponse threadResponse) {
        var listMessageResponse =
                this.httpExecutor.executeWithPathVariables(threadResponse.id());

        return new MessageAnswersRetrievalTypeStage(listMessageResponse);
    }

    public MessageAnswersRetrievalTypeStage answers(String threadId) {
        var listMessageResponse =
                this.httpExecutor.executeWithPathVariables(threadId);

        return new MessageAnswersRetrievalTypeStage(listMessageResponse);
    }
}