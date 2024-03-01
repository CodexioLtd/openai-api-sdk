package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

import java.io.File;

public class FileUploadingAsyncContextStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O>
        implements RuntimeExecutor {

    FileUploadingAsyncContextStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public FileUploadingAsyncPromise<O> feed(File file) {
        return new FileUploadingAsyncPromise<>(
                this.executor,
                this.requestBuilder.withFile(file)
        );
    }
}
