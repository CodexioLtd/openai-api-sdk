package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

public class FileDownloadingConfigurationStage
        extends FileConfigurationStage {

    protected final RetrieveFileContentHttpExecutor executor;

    protected final FileDownloadingMeta.Builder fileDownloadingMeta;

    public FileDownloadingConfigurationStage(
            RetrieveFileContentHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(requestBuilder);
        this.executor = executor;
        this.fileDownloadingMeta = fileDownloadingMeta;
    }
}