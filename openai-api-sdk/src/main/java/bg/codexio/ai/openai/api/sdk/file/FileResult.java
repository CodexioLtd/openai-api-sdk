package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingRuntimeSelectionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.file.constants.FileLoggerConstants.FILE_DOWNLOAD_SUCCESS;

public record FileResult(
        String id,
        String fileName
) {

    static Logger log = LoggerFactory.getLogger(FileResult.class);

    public static Builder builder() {
        return new Builder(
                null,
                null
        );
    }

    public record Builder(
            String id,
            String fileName
    ) {

        public Builder withId(String id) {
            return new Builder(
                    id,
                    fileName
            );
        }

        public Builder withFileName(String fileName) {
            return new Builder(
                    id,
                    fileName
            );
        }

        public FileResult build() {
            return new FileResult(
                    id,
                    fileName
            );
        }
    }

    public File downloadImmediate(File targetFolder) throws IOException {
        return Files.defaults()
                    .and()
                    .download(this)
                    .immediate()
                    .toFolder(targetFolder);
    }

    public void downloadAsync(File targetFolder) {
        Files.defaults()
             .and()
             .download(this)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(file -> log.info(
                     FILE_DOWNLOAD_SUCCESS,
                     file.getName()
             ));
    }

    public void downloadAsync(
            File targetFolder,
            Consumer<File> consumer
    ) {
        Files.defaults()
             .and()
             .download(this)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(consumer);
    }

    public void downloadReactive(File targetFolder) {
        Files.defaults()
             .and()
             .download(this)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(file -> log.info(
                     FILE_DOWNLOAD_SUCCESS,
                     file.getName()
             ));
    }

    public void downloadReactive(
            File targetFolder,
            Consumer<File> consumer
    ) {
        Files.defaults()
             .and()
             .download(this)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(consumer);
    }

    public File downloadImmediate(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        return Files.authenticate(auth)
                    .and()
                    .download(this)
                    .immediate()
                    .toFolder(targetFolder);
    }

    public void downloadAsync(
            File targetFolder,
            SdkAuth auth
    ) {
        Files.authenticate(auth)
             .and()
             .download(this)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(file -> log.info(
                     FILE_DOWNLOAD_SUCCESS,
                     file.getName()
             ));
    }

    public void downloadAsync(
            File targetFolder,
            SdkAuth auth,
            Consumer<File> consumer
    ) {
        Files.authenticate(auth)
             .and()
             .download(this)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(consumer);
    }

    public void downloadReactive(
            File targetFolder,
            SdkAuth auth
    ) {
        Files.authenticate(auth)
             .and()
             .download(this)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(file -> log.info(
                     FILE_DOWNLOAD_SUCCESS,
                     file.getName()
             ));
    }

    public void downloadReactive(
            File targetFolder,
            SdkAuth auth,
            Consumer<File> consumer
    ) {
        Files.authenticate(auth)
             .and()
             .download(this)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(consumer);
    }

    public File downloadImmediate(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        return Files.authenticate(context)
                    .and()
                    .download(this)
                    .immediate()
                    .toFolder(targetFolder);
    }

    public void downloadAsync(
            File targetFolder,
            HttpExecutorContext context
    ) {
        Files.authenticate(context)
             .and()
             .download(this)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(file -> log.info(
                     FILE_DOWNLOAD_SUCCESS,
                     file.getName()
             ));
    }

    public void downloadAsync(
            File targetFolder,
            HttpExecutorContext context,
            Consumer<File> consumer
    ) {
        Files.authenticate(context)
             .and()
             .download(this)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(consumer);
    }

    public void downloadReactive(
            File targetFolder,
            HttpExecutorContext context
    ) {
        Files.authenticate(context)
             .and()
             .download(this)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(file -> log.info(
                     FILE_DOWNLOAD_SUCCESS,
                     file.getName()
             ));
    }

    public void downloadReactive(
            File targetFolder,
            HttpExecutorContext context,
            Consumer<File> consumer
    ) {
        Files.authenticate(context)
             .and()
             .download(this)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(consumer);
    }

    public File downloadImmediate(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        return this.toRuntimeSelection(httpExecutor)
                   .immediate()
                   .toFolder(targetFolder);
    }

    public void downloadAsync(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) {
        this.toRuntimeSelection(httpExecutor)
            .async()
            .downloadTo(targetFolder)
            .whenDownloaded(file -> log.info(
                    FILE_DOWNLOAD_SUCCESS,
                    file.getName()
            ));
    }

    public void downloadAsync(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor,
            Consumer<File> consumer
    ) {
        this.toRuntimeSelection(httpExecutor)
            .async()
            .downloadTo(targetFolder)
            .whenDownloaded(consumer);
    }

    public void downloadReactive(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) {
        this.toRuntimeSelection(httpExecutor)
            .reactive()
            .toFolder(targetFolder)
            .subscribe(file -> log.info(
                    FILE_DOWNLOAD_SUCCESS,
                    file.getName()
            ));
    }

    public void downloadReactive(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor,
            Consumer<File> consumer
    ) {
        this.toRuntimeSelection(httpExecutor)
            .reactive()
            .toFolder(targetFolder)
            .subscribe(consumer);
    }

    private FileDownloadingRuntimeSelectionStage toRuntimeSelection(RetrieveFileContentHttpExecutor httpExecutor) {
        return Files.throughHttp(
                            httpExecutor,
                            this.id
                    )
                    .as(this.fileName);
    }
}