package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.io.File;

public class FileDownloadingAsyncContextStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {

    public FileDownloadingAsyncContextStage(
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

    public FileDownloadingAsyncPromise downloadTo(File targetFolder) {
        return new FileDownloadingAsyncPromise(
                this.executor,
                this.requestBuilder,
                this.fileDownloadingMeta.withTargetFolder(targetFolder)
        );
    }
}