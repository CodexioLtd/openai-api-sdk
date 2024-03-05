package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import java.io.File;
import java.io.IOException;

public record FileResult(
        String id,
        String fileName
) {

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

    public File download(File targetFolder) throws IOException {
        return Files.defaults()
                    .and()
                    .download(this)
                    .immediate()
                    .toFolder(targetFolder);
    }

    public File download(
            File targetFolder,
            SdkAuth auth
    ) throws IOException {
        return Files.authenticate(auth)
                    .and()
                    .download(this)
                    .immediate()
                    .toFolder(targetFolder);
    }

    public File download(
            File targetFolder,
            HttpExecutorContext context
    ) throws IOException {
        return Files.authenticate(context)
                    .and()
                    .download(this)
                    .immediate()
                    .toFolder(targetFolder);
    }

    public File download(
            File targetFolder,
            RetrieveFileContentHttpExecutor httpExecutor
    ) throws IOException {
        return Files.throughHttp(
                            httpExecutor,
                            this.id
                    )
                    .as(this.fileName)
                    .immediate()
                    .toFolder(targetFolder);
    }
}