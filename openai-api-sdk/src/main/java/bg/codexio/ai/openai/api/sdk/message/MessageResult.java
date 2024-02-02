package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;

import java.io.File;
import java.io.IOException;

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
        this.fileResult.build()
                       .download(targetFolder);

        return this.message;
    }

    public String download(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        this.fileResult.build()
                       .download(
                               targetFolder,
                               auth
                       );

        return this.message;
    }

    public String download(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        this.fileResult.build()
                       .download(
                               targetFolder,
                               context
                       );

        return this.message;
    }

    public String download(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        this.fileResult.build()
                       .download(
                               targetFolder,
                               httpExecutor
                       );

        return this.message;
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