package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public abstract class FileUploadingConfigurationStage {

    protected final UploadFileHttpExecutor executor;

    protected final UploadFileRequest.Builder requestBuilder;

    FileUploadingConfigurationStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}