package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

import java.util.function.Consumer;

public class FileUploadingAsyncPromise<O extends Mergeable<O>>
        extends FileConfigurationStage<O> {

    private final Consumer<String> onEachLine = x -> {};

    FileUploadingAsyncPromise(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public void then(Consumer<O> afterAll) {
        this.executor.executeAsync(
                this.requestBuilder.build(),
                this.onEachLine,
                afterAll
        );
    }
}
