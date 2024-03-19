package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.download.FileDownloadExecutor;

import java.io.File;
import java.io.IOException;

public class FileDownloadingImmediateContextStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {

    public FileDownloadingImmediateContextStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
    }

    public File toFolder(File targetFolder) throws IOException {
        return new FileDownloadExecutor().downloadTo(
                targetFolder,
                this.executor.executeWithPathVariables(this.fileDownloadingMeta.fileId()),
                this.fileDownloadingMeta.fileName()
        );
    }
}