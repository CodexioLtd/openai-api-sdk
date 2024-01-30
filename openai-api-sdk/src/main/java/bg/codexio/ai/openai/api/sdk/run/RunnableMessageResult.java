package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.MessageResult;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.message.Messages;

public class RunnableMessageResult
        extends RunnableConfigurationStage {

    RunnableMessageResult(
            RunnableHttpExecutor httpExecutor,
            RunnableRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public MessageResult answers() {
        return Messages.defaults(this.threadId)
                       .and()
                       .respond()
                       .answers();
    }
}