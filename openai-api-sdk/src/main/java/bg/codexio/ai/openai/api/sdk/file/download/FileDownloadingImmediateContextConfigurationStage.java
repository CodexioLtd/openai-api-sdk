package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutor;

public class FileDownloadingImmediateContextConfigurationStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {
    public FileDownloadingImmediateContextConfigurationStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
    }

    public FileDownloadingImmediateContextStage standart() {
        return new FileDownloadingImmediateContextStage(
                this.executor,
                this.fileDownloadingMeta
        );
    }

    public FileDownloadingImmediateContextStage executor(DownloadExecutor downloadExecutor) {
        return new FileDownloadingImmediateContextStage(
                this.executor,
                this.fileDownloadingMeta,
                downloadExecutor
        );
    }
}