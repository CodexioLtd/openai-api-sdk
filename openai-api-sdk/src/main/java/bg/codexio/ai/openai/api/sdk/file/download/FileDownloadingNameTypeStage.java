package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public class FileDownloadingNameTypeStage
        extends FileDownloadingConfigurationStage {

    public FileDownloadingNameTypeStage(
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

    public FileDownloadingRuntimeSelectionStage as(String fileName) {
        return new FileDownloadingRuntimeSelectionStage(
                this.executor,
                this.requestBuilder,
                this.fileDownloadingMeta.withFileName(fileName)
        );
    }
}