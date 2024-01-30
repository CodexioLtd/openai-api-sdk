package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;

import java.io.File;
import java.io.IOException;

public class FileDownloadingStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O> {

    private final String fileId;
    private final String fileName;

    FileDownloadingStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder,
            String fileId,
            String fileName
    ) {
        super(
                executor,
                requestBuilder
        );
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public File toFolder(File targetFolder) throws IOException {
        return DownloadExecutor.downloadTo(
                targetFolder,
                (FileContentResponse) this.executor.executeWithPathVariables(fileId),
                this.fileName
        );
    }
}