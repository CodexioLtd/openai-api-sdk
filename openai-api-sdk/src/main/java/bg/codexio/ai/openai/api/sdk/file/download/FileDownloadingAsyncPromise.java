package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.download.FileDownloadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FileDownloadingAsyncPromise
        extends FileDownloadingConfigurationStage {

    private final Logger log =
            LoggerFactory.getLogger(FileDownloadingAsyncPromise.class);

    private final Consumer<String> onEachLine = x -> {};

    public FileDownloadingAsyncPromise(
            RetrieveFileContentHttpExecutor executor,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                fileDownloadingMeta
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
        this.executor.executeAsyncWithPathVariables(
                this.onEachLine,
                response -> CompletableFuture.supplyAsync(() -> {
                                                 try {
                                                     return new FileDownloadExecutor().downloadTo(
                                                             this.fileDownloadingMeta.targetFolder(),
                                                             response,
                                                             this.fileDownloadingMeta.fileName()
                                                     );
                                                 } catch (IOException e) {
                                                     throw new RuntimeException(e);
                                                 }
                                             })
                                             .whenComplete((result, error) -> {
                                                 if (error != null) {
                                                     errorHandler.accept(error);
                                                 } else {
                                                     callback.accept(result);
                                                 }
                                             }),
                this.fileDownloadingMeta.fileId()
        );
    }
}
