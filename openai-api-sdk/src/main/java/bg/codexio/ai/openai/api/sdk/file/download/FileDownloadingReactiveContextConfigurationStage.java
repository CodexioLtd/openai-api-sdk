package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutor;

public class FileDownloadingReactiveContextConfigurationStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {

    public FileDownloadingReactiveContextConfigurationStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
    }

    public FileDownloadingReactiveContextStage standart() {
        return new FileDownloadingReactiveContextStage(
                this.executor,
                this.fileDownloadingMeta
        );
    }

    public FileDownloadingReactiveContextStage executor(DownloadExecutor downloadExecutor) {
        return new FileDownloadingReactiveContextStage(
                this.executor,
                this.fileDownloadingMeta,
                downloadExecutor
        );
    }
}