package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.create.ThreadCreationStage;
import bg.codexio.ai.openai.api.sdk.thread.modify.ThreadModificationStage;

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

    public ThreadCreationStage creating() {
        return new ThreadCreationStage(
                this.createThreadHttpExecutor,
                ThreadCreationRequest.builder()
        );
    }

    public ThreadModificationStage modify(String threadId) {
        return new ThreadModificationStage(
                this.modifyThreadHttpExecutor,
                ThreadModificationRequest.builder(),
                threadId
        );
    }

    public ThreadModificationStage modify(ThreadResponse thread) {
        return new ThreadModificationStage(
                this.modifyThreadHttpExecutor,
                ThreadModificationRequest.builder(),
                thread.id()
        );
    }
}