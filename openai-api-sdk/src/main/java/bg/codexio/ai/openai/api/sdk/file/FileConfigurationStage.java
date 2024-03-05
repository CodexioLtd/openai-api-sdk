package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public abstract class FileConfigurationStage {

    protected final UploadFileRequest.Builder requestBuilder;

    public FileConfigurationStage(UploadFileRequest.Builder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }
}