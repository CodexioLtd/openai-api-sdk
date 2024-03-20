package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class FileDownloadingRuntimeSelectionStage
        extends FileDownloadingConfigurationStage
        implements RuntimeSelectionStage {


    public FileDownloadingRuntimeSelectionStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
    }

    @Override
    public FileDownloadingImmediateContextConfigurationStage immediate() {
        return new FileDownloadingImmediateContextConfigurationStage(
                this.executor,
                this.fileDownloadingMeta
        );
    }

    @Override
    public FileDownloadingAsyncContextStage async() {
        return new FileDownloadingAsyncContextStage(
                this.executor,
                this.fileDownloadingMeta
        );
    }

    @Override
    public FileDownloadingReactiveContextStage reactive() {
        return new FileDownloadingReactiveContextStage(
                this.executor,
                this.fileDownloadingMeta
        );
    }
}
