package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;

public class FileDownloadingConfigurationStage {

    protected final RetrieveFileContentHttpExecutor executor;

    protected final FileDownloadingMeta.Builder fileDownloadingMeta;

    public FileDownloadingConfigurationStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        this.executor = executor;
        this.fileDownloadingMeta = fileDownloadingMeta;
    }
}