package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public abstract class FileConfigurationStage<O extends Mergeable<O>> {

    protected final DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor;

    protected final UploadFileRequest.Builder requestBuilder;

    public FileConfigurationStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        this.executor = executor;
        this.requestBuilder = requestBuilder;
    }
}