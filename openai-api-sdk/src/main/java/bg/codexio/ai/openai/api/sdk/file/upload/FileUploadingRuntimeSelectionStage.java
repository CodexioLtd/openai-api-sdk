package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

public class FileUploadingRuntimeSelectionStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O>
        implements RuntimeSelectionStage {

    FileUploadingRuntimeSelectionStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    @Override
    public FileUploadingImmediateContextStage immediate() {
        return new FileUploadingImmediateContextStage(
                (UploadFileHttpExecutor) this.executor,
                this.requestBuilder
        );
    }

    @Override
    public FileUploadingAsyncContextStage<O> async() {
        return new FileUploadingAsyncContextStage<>(
                this.executor,
                this.requestBuilder
        );
    }

    @Override
    public FileUploadingReactiveContextStage<O> reactive() {
        return new FileUploadingReactiveContextStage<>(
                this.executor,
                this.requestBuilder
        );
    }
}
