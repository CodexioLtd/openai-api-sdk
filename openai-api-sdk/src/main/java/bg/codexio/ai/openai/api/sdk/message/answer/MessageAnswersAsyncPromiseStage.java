package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;

import java.util.function.Consumer;

public class MessageAnswersAsyncPromiseStage
        extends MessageAnswersConfigurationStage {
    protected MessageAnswersAsyncPromiseStage(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        super(
                httpExecutor,
                threadId
        );
    }

    public void then(Consumer<ListMessagesResponse> afterAll) {
        this.then(
                x -> {},
                afterAll
        );
    }

    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {}
        );
    }

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
