package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutor;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutorFactory;
import bg.codexio.ai.openai.api.sdk.download.context.DefaultDownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.context.DownloadExecutorFactoryContext;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.constant.FactoryConstantsUtils.FILE_EXECUTOR_IDENTIFIER;

public class FileDownloadingImmediateContextStage
        extends FileDownloadingConfigurationStage {

    private final DownloadExecutor downloadExecutor;

    public FileDownloadingImmediateContextStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta,
            DownloadExecutor downloadExecutor
    ) {
        super(
                executor,
                fileDownloadingMeta
        );
        this.downloadExecutor = downloadExecutor;
    }

    public FileDownloadingImmediateContextStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta,
            DownloadExecutorFactory downloadExecutorFactory
    ) {
        this(
                executor,
                fileDownloadingMeta,
                downloadExecutorFactory.create(FILE_EXECUTOR_IDENTIFIER)
        );
    }

    public FileDownloadingImmediateContextStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta,
            DownloadExecutorFactoryContext downloadExecutorFactoryContext
    ) {
        this(
                executor,
                fileDownloadingMeta,
                downloadExecutorFactoryContext.getDownloadExecutorFactory()
        );
    }

    public FileDownloadingImmediateContextStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        this(
                executor,
                fileDownloadingMeta,
                DefaultDownloadExecutorFactoryContext.getInstance()
        );
    }

    public File toFolder(File targetFolder) throws IOException {
        return this.downloadExecutor.downloadTo(
                targetFolder,
                this.executor.immediate()
                             .retrieve(this.fileDownloadingMeta.fileId()),
                this.fileDownloadingMeta.fileName()
        );
    }
}