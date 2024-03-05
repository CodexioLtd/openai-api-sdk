package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.util.function.Consumer;

public class FileUploadingAsyncPromise
        extends FileUploadingConfigurationStage {

    private final Consumer<String> onEachLine = x -> {};

    FileUploadingAsyncPromise(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }


    public void then(Consumer<FileResponse> afterAll) {
        this.executor.executeAsync(
                this.requestBuilder.build(),
                this.onEachLine,
                afterAll
        );
    }
}