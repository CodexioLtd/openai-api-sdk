package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutor;

public class FileDownloadingAsyncContextConfigurationStage
        extends FileDownloadingConfigurationStage {

    public FileDownloadingAsyncContextConfigurationStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
    }

    public FileDownloadingAsyncPromise standart() {
        return new FileDownloadingAsyncPromise(
                this.executor,
                this.fileDownloadingMeta
        );
    }

    public FileDownloadingAsyncPromise executor(DownloadExecutor downloadExecutor) {
        return new FileDownloadingAsyncPromise(
                this.executor,
                this.fileDownloadingMeta,
                downloadExecutor
        );
    }
}