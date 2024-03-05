package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.io.File;

public class FileUploadingAsyncContextStage
        extends FileUploadingConfigurationStage
        implements RuntimeExecutor {

    FileUploadingAsyncContextStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public FileUploadingAsyncPromise feed(File file) {
        return new FileUploadingAsyncPromise(
                this.executor,
                this.requestBuilder.withFile(file)
        );
    }
}