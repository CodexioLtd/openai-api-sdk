package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public abstract class FileConfigurationStage {

    protected final UploadFileHttpExecutor executor;
    protected final UploadFileRequest.Builder requestContext;

    FileConfigurationStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestContext
    ) {
        this.executor = executor;
        this.requestContext = requestContext;
    }
}