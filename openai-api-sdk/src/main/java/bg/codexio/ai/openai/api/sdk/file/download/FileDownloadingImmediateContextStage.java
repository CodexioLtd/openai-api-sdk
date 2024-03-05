package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.io.File;
import java.io.IOException;

public class FileDownloadingImmediateContextStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {

    public FileDownloadingImmediateContextStage(
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

    public File toFolder(File targetFolder) throws IOException {
        return DownloadExecutor.downloadTo(
                targetFolder,
                this.executor.executeWithPathVariables(this.fileDownloadingMeta.fileId()),
                this.fileDownloadingMeta.fileName()
        );
    }
}