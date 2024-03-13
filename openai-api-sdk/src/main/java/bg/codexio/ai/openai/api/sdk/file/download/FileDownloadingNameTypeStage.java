package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;

public class FileDownloadingNameTypeStage
        extends FileDownloadingConfigurationStage {

    public FileDownloadingNameTypeStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
    }

    public FileDownloadingRuntimeSelectionStage as(String fileName) {
        return new FileDownloadingRuntimeSelectionStage(
                this.executor,
                this.fileDownloadingMeta.withFileName(fileName)
        );
    }
}