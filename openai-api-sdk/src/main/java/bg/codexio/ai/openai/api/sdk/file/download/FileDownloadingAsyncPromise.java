package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutor;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutorFactory;
import bg.codexio.ai.openai.api.sdk.download.context.DefaultDownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.context.DownloadExecutorFactoryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.constant.FactoryConstantsUtils.FILE_EXECUTOR_IDENTIFIER;

public class FileDownloadingAsyncPromise
        extends FileDownloadingConfigurationStage {

    private final Logger log =
            LoggerFactory.getLogger(FileDownloadingAsyncPromise.class);

    private final Consumer<String> onEachLine = x -> {};

    private final DownloadExecutor downloadExecutor;

    public FileDownloadingAsyncPromise(
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

    public FileDownloadingAsyncPromise(
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

    public FileDownloadingAsyncPromise(
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

    public FileDownloadingAsyncPromise(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        this(
                executor,
                fileDownloadingMeta,
                DefaultDownloadExecutorFactoryContext.getInstance()
        );
    }

    public void whenDownloaded(Consumer<File> callback) {
        this.whenDownloaded(
                callback,
                error -> log.error(
                        "File downloading error.",
                        error
                )
        );
    }

    public void whenDownloaded(
            Consumer<File> callback,
            Consumer<Throwable> errorHandler
    ) {
        this.executor.async()
                     .retrieve(
                             this.onEachLine,
                             response -> CompletableFuture.supplyAsync(() -> {
                                                              try {
                                                                  return this.downloadExecutor.downloadTo(
                                                                          this.fileDownloadingMeta.targetFolder(),
                                                                          response,
                                                                          this.fileDownloadingMeta.fileName()
                                                                  );
                                                              } catch (IOException e) {
                                                                  throw new RuntimeException(e);
                                                              }
                                                          })
                                                          .whenComplete((result, error) -> {
                                                              if (error
                                                                      != null) {
                                                                  errorHandler.accept(error);
                                                              } else {
                                                                  callback.accept(result);
                                                              }
                                                          }),
                             this.fileDownloadingMeta.fileId()
                     );
    }
}