package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

public abstract class FileUploadingConfigurationStage
        extends FileConfigurationStage {

    protected final UploadFileHttpExecutor executor;

    FileUploadingConfigurationStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(requestBuilder);
        this.executor = executor;
    }
}