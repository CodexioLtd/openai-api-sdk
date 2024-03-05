package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.purpose.Purpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public class FileTargetingStage
        extends FileUploadingConfigurationStage {

    public FileTargetingStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public FileUploadingRuntimeSelectionStage targeting(Purpose purpose) {
        return new FileUploadingRuntimeSelectionStage(
                this.executor,
                this.requestBuilder.withPurpose(purpose.name())
        );
    }

    public FileUploadingRuntimeSelectionStage forAssistants() {
        return this.targeting(new AssistantPurpose());
    }
}