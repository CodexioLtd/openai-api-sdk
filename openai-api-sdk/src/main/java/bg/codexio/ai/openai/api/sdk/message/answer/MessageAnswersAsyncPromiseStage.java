package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

import java.util.function.Consumer;

public class MessageAnswersAsyncPromiseStage
        extends MessageAnswersConfigurationStage
        implements AsyncPromiseStage<ListMessagesResponse> {
    protected MessageAnswersAsyncPromiseStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(
                httpExecutor,
                threadId
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<ListMessagesResponse> afterAll
    ) {
        this.httpExecutor.executeAsyncWithPathVariables(
                onEachLine,
                afterAll,
                this.threadId
        );
    }
}
