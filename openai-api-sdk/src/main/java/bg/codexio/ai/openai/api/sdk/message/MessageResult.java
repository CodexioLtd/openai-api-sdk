package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static bg.codexio.ai.openai.api.sdk.message.constant.MessageConstants.*;

public record MessageResult(
        String message,
        FileResult.Builder fileResult,
        String imageFileId
) {
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

    public String download(File targetFolder) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .download(targetFolder);
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImage(targetFolder);

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

    public String download(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .download(
                                   targetFolder,
                                   auth
                           );
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImage(
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

    public String download(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .download(
                                   targetFolder,
                                   context
                           );
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImage(
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

    public String download(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        if (Objects.nonNull(this.fileResult)) {
            this.fileResult.build()
                           .download(
                                   targetFolder,
                                   httpExecutor
                           );
        } else if (Objects.nonNull(this.imageFileId)) {
            this.downloadImage(
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

    private void downloadImage(File targetFolder) throws IOException {
        Files.defaults()
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .toFolder(targetFolder);

    }

    private void downloadImage(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        Files.authenticate(auth)
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .toFolder(targetFolder);
    }

    private void downloadImage(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        Files.authenticate(context)
             .and()
             .download(this.imageFileId)
             .as(IMAGE_FILE_EXTENSION)
             .toFolder(targetFolder);

    }

    private void downloadImage(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        Files.throughHttp(
                     httpExecutor,
                     this.imageFileId
             )
             .as(IMAGE_FILE_EXTENSION)
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