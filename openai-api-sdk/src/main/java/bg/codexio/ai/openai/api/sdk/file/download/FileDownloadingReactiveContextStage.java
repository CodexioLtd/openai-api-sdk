package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutor;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutorFactory;
import bg.codexio.ai.openai.api.sdk.download.context.DefaultDownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.context.DownloadExecutorFactoryContext;
import reactor.core.publisher.Mono;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.constant.FactoryConstantsUtils.FILE_EXECUTOR_IDENTIFIER;

public class FileDownloadingReactiveContextStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {

    private final DownloadExecutor downloadExecutor;

    public FileDownloadingReactiveContextStage(
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

    public FileDownloadingReactiveContextStage(
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

    public FileDownloadingReactiveContextStage(
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

    public FileDownloadingReactiveContextStage(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        this(
                executor,
                fileDownloadingMeta,
                DefaultDownloadExecutorFactoryContext.getInstance()
        );
    }

    public Mono<File> toFolder(File targetFolder) {
        return this.executor.executeReactiveWithPathVariables(this.fileDownloadingMeta.fileId())
                            .response()
                            .handle((response, sink) -> {
                                try {
                                    sink.next(this.downloadExecutor.downloadTo(
                                            targetFolder,
                                            response,
                                            this.fileDownloadingMeta.fileName()
                                    ));
                                } catch (Exception e) {
                                    sink.error(e);
                                }
                            });
    }
}