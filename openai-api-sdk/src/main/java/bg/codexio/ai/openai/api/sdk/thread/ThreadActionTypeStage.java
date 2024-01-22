package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class ThreadActionTypeStage {

    private final CreateThreadHttpExecutor createThreadHttpExecutor;

    private final ModifyThreadHttpExecutor modifyThreadHttpExecutor;

    public ThreadActionTypeStage(
            CreateThreadHttpExecutor createThreadHttpExecutor,
            ModifyThreadHttpExecutor modifyThreadHttpExecutor
    ) {
        this.createThreadHttpExecutor = createThreadHttpExecutor;
        this.modifyThreadHttpExecutor = modifyThreadHttpExecutor;
    }

    public ThreadCreationStage<ThreadCreationRequest> creating() {
        return new ThreadCreationStage<>(
                this.createThreadHttpExecutor,
                ThreadRequestBuilder.<ThreadCreationRequest>builder()
                                    .withSpecificRequestCreator(threadRequestBuilder -> new ThreadCreationRequest(
                                            threadRequestBuilder.messages(),
                                            threadRequestBuilder.metadata()
                                    ))
        );
    }

    public ThreadModificationStage<ThreadModificationRequest> modify(String threadId) {
        return new ThreadModificationStage<>(
                this.modifyThreadHttpExecutor,
                ThreadRequestBuilder.<ThreadModificationRequest>builder()
                                    .withSpecificRequestCreator(threadRequestBuilder -> new ThreadModificationRequest(threadRequestBuilder.metadata())),
                threadId
        );
    }

    public ThreadModificationStage<ThreadModificationRequest> modify(ThreadResponse thread) {
        return new ThreadModificationStage<>(
                this.modifyThreadHttpExecutor,
                ThreadRequestBuilder.<ThreadModificationRequest>builder()
                                    .withSpecificRequestCreator(threadRequestBuilder -> new ThreadModificationRequest(threadRequestBuilder.metadata())),
                thread.id()
        );
    }
}