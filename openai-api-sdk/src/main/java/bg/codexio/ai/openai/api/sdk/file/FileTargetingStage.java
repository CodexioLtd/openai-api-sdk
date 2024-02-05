package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.purpose.Purpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

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

    public FileUploadingStage targeting(Purpose purpose) {
        return new FileUploadingStage(
                (UploadFileHttpExecutor) this.executor,
                this.requestBuilder.withPurpose(purpose.name())
        );
    }

    public FileUploadingStage forAssistants() {
        return this.targeting(new AssistantPurpose());
    }
}