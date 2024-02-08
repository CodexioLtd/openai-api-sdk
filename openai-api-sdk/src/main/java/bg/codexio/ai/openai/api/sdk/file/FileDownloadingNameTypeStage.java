package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;

public class FileDownloadingNameTypeStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O> {

    private final String fileId;

    public FileDownloadingNameTypeStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder,
            String fileId
    ) {
        super(
                executor,
                requestBuilder
        );
        this.fileId = fileId;
    }

    public FileDownloadingStage<O> as(String fileName) {
        return new FileDownloadingStage<>(
                this.executor,
                this.requestBuilder,
                this.fileId,
                fileName
        );
    }
}