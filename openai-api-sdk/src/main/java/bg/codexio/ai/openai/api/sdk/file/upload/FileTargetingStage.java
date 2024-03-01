package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.purpose.Purpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

public class FileTargetingStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O> {

    FileTargetingStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    public FileUploadingRuntimeSelectionStage<O> targeting(Purpose purpose) {
        return new FileUploadingRuntimeSelectionStage<>(
                this.executor,
                this.requestBuilder.withPurpose(purpose.name())
        );
    }

    public FileUploadingRuntimeSelectionStage<O> forAssistants() {
        return this.targeting(new AssistantPurpose());
    }
}