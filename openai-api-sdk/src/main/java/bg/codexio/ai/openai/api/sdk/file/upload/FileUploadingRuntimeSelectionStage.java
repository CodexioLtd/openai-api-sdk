package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class FileUploadingRuntimeSelectionStage
        extends FileUploadingConfigurationStage
        implements RuntimeSelectionStage {

    FileUploadingRuntimeSelectionStage(
            UploadFileHttpExecutor executor,
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
                this.executor,
                this.requestBuilder
        );
    }

    @Override
    public FileUploadingAsyncContextStage async() {
        return new FileUploadingAsyncContextStage(
                this.executor,
                this.requestBuilder
        );
    }

    @Override
    public FileUploadingReactiveContextStage reactive() {
        return new FileUploadingReactiveContextStage(
                this.executor,
                this.requestBuilder
        );
    }
}