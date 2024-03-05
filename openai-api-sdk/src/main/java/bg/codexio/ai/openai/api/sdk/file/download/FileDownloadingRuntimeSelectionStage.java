package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class FileDownloadingRuntimeSelectionStage
        extends FileDownloadingConfigurationStage
        implements RuntimeSelectionStage {


    public FileDownloadingRuntimeSelectionStage(
            RetrieveFileContentHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                requestBuilder,
                fileDownloadingMeta
        );
    }

    @Override
    public FileDownloadingImmediateContextStage immediate() {
        return new FileDownloadingImmediateContextStage(
                this.executor,
                this.requestBuilder,
                this.fileDownloadingMeta
        );
    }

    @Override
    public FileDownloadingAsyncContextStage async() {
        return new FileDownloadingAsyncContextStage(
                this.executor,
                this.requestBuilder,
                this.fileDownloadingMeta
        );
    }

    @Override
    public FileDownloadingReactiveContextStage reactive() {
        return new FileDownloadingReactiveContextStage(
                this.executor,
                this.requestBuilder,
                this.fileDownloadingMeta
        );
    }
}
