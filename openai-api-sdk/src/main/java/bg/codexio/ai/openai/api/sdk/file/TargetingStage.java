package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.purpose.Purpose;

public class TargetingStage
        extends FileConfigurationStage {

    TargetingStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    public UploadFileStage targeting(Purpose purpose) {
        return new UploadFileStage(
                this.executor,
                this.requestContext.withPurpose(purpose.getName())
        );
    }

    public UploadFileStage forAssistants() {
        return this.targeting(new AssistantPurpose());
    }
}