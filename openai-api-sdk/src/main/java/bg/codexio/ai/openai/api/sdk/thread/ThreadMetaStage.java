package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class ThreadMetaStage
        extends ThreadConfigurationStage {
    protected ThreadMetaStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestContext
    ) {
        super(
                httpExecutor,
                requestContext
        );
    }

    public ThreadResponse awareOf(String... metadata) {
        return this.httpExecutor.execute(this.requestContext.addMetadata(metadata)
                                                            .build());
    }

    public MessageThreadChatStage withMeta(String... metadata) {
        return new MessageThreadChatStage(
                this.httpExecutor,
                this.requestContext.addMetadata(metadata)
        );
    }
}
