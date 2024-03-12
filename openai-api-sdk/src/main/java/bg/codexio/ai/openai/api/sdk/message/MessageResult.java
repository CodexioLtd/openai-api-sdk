package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.message.constant.MessageConstants.*;
import static bg.codexio.ai.openai.api.sdk.message.constant.MessageLoggerConstants.MESSAGE_IMAGE_DOWNLOAD_SUCCESS;

public record MessageResult(
        String message,
        FileResult.Builder fileResult,
        String imageFileId
) {
    static Logger log = LoggerFactory.getLogger(MessageResult.class);

    public static MessageResult empty() {
        return new MessageResult(
                null,
                null,
                null
        );
    }

    public static Builder builder() {
        return new Builder(
                null,
                null,
                null
        );
    }

    public FileResult file() {
        return this.fileResult.build();
    }

    public String downloadImmediate(File targetFolder) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .downloadImmediate(targetFolder);
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImageImmediate(targetFolder);

            return Objects.requireNonNullElse(
                    this.message,
                    CREATED_IMAGE_FILE_MESSAGE
            );
        }

        return Objects.requireNonNullElse(
                this.message,
                EMPTY
        );
    }

    public void downloadAsync(
            File targetFolder,
            Consumer<String> result
    ) {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .downloadAsync(targetFolder);
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImageAsync(targetFolder);

            result.accept(Objects.requireNonNullElse(
                    this.message,
                    CREATED_IMAGE_FILE_MESSAGE
            ));
        }

        result.accept(Objects.requireNonNullElse(
                this.message,
                EMPTY
        ));
    }

    public String downloadReactive(
            File targetFolder
    ) {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .downloadReactive(targetFolder);
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImageReactive(targetFolder);

            return Objects.requireNonNullElse(
                    this.message,
                    CREATED_IMAGE_FILE_MESSAGE
            );
        }

        return Objects.requireNonNullElse(
                this.message,
                EMPTY
        );
    }

    public String downloadImmediate(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .downloadImmediate(
                                   targetFolder,
                                   auth
                           );
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImageImmediate(
                    targetFolder,
                    auth
            );

            return Objects.requireNonNullElse(
                    this.message,
                    CREATED_IMAGE_FILE_MESSAGE
            );
        }

        return Objects.requireNonNullElse(
                this.message,
                EMPTY
        );
    }

    public String downloadImmediate(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .downloadImmediate(
                                   targetFolder,
                                   context
                           );
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImageImmediate(
                    targetFolder,
                    context
            );

            return Objects.requireNonNullElse(
                    this.message,
                    CREATED_IMAGE_FILE_MESSAGE
            );
        }

        return Objects.requireNonNullElse(
                this.message,
                EMPTY
        );
    }

    public String downloadImmediate(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .downloadImmediate(
                                   targetFolder,
                                   httpExecutor
                           );
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImageImmediate(
                    targetFolder,
                    httpExecutor
            );

            return Objects.requireNonNullElse(
                    this.message,
                    CREATED_IMAGE_FILE_MESSAGE
            );
        }

        return Objects.requireNonNullElse(
                this.message,
                EMPTY
        );
    }

    private void downloadImageImmediate(File targetFolder) throws IOException {
        Files.defaults()
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .immediate()
             .toFolder(targetFolder);
    }

    private void downloadImageAsync(File targetFolder) {
        Files.defaults()
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .async()
             .downloadTo(targetFolder)
             .whenDownloaded(image -> log.info(
                     MESSAGE_IMAGE_DOWNLOAD_SUCCESS,
                     image.getName()
             ));
    }

    private void downloadImageReactive(File targetFolder) {
        Files.defaults()
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .reactive()
             .toFolder(targetFolder)
             .subscribe(image -> log.info(
                     MESSAGE_IMAGE_DOWNLOAD_SUCCESS,
                     image.getName()
             ));
    }

    private void downloadImageImmediate(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        Files.authenticate(auth)
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .immediate()
             .toFolder(targetFolder);
    }

    private void downloadImageImmediate(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        Files.authenticate(context)
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .immediate()
             .toFolder(targetFolder);

    }

    private void downloadImageImmediate(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        Files.throughHttp(
                     httpExecutor,
                     this.imageFileId
             )
             .as(IMAGE_FILE_EXTENSION)
             .immediate()
             .toFolder(targetFolder);
    }

    public record Builder(
            String message,
            FileResult.Builder fileResult,
            String imageFileId
    ) {
        public Builder withMessage(String message) {
            return new Builder(
                    message,
                    fileResult,
                    imageFileId
            );
        }

        public Builder withFileResult(FileResult.Builder fileResult) {
            return new Builder(
                    message,
                    fileResult,
                    imageFileId
            );
        }

        public Builder withImageFileId(String imageFileId) {
            return new Builder(
                    message,
                    fileResult,
                    imageFileId
            );
        }

        public MessageResult build() {
            return new MessageResult(
                    message,
                    fileResult,
                    imageFileId
            );
        }
    }
}