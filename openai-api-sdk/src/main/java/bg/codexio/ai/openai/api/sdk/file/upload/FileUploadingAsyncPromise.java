package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

import java.util.function.Consumer;

public class FileUploadingAsyncPromise
        extends FileUploadingConfigurationStage
        implements AsyncPromiseStage<FileResponse> {

    FileUploadingAsyncPromise(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<FileResponse> afterAll
    ) {
        this.executor.async()
                     .execute(
                             this.requestBuilder.build(),
                             onEachLine,
                             afterAll
                     );
    }
}