package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.purpose.Purpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public class FileTargetingStage
        extends FileConfigurationStage {

    FileTargetingStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    public FileUploadingStage targeting(Purpose purpose) {
        return new FileUploadingStage(
                this.executor,
                this.requestBuilder.withPurpose(purpose.name())
        );
    }

    public FileUploadingStage forAssistants() {
        return this.targeting(new AssistantPurpose());
    }
}