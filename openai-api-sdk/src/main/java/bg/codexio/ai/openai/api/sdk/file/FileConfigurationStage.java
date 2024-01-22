package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public abstract class FileConfigurationStage {

    protected final UploadFileHttpExecutor executor;
    protected final UploadFileRequest.Builder requestBuilder;

    FileConfigurationStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}