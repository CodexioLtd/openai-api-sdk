package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingNameTypeStage;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingStage;
import bg.codexio.ai.openai.api.sdk.file.upload.FileTargetingStage;

public class FileActionTypeStage {

    private final UploadFileHttpExecutor uploadFileHttpExecutor;
    private final RetrieveFileContentHttpExecutor retrieveFileContentHttpExecutor;

    public FileActionTypeStage(
            UploadFileHttpExecutor uploadFileHttpExecutor,
            RetrieveFileContentHttpExecutor retrieveFileContentHttpExecutor
    ) {
        this.uploadFileHttpExecutor = uploadFileHttpExecutor;
        this.retrieveFileContentHttpExecutor = retrieveFileContentHttpExecutor;
    }

    public FileTargetingStage<FileResponse> upload() {
        return new FileTargetingStage<>(
                this.uploadFileHttpExecutor,
                UploadFileRequest.builder()
        );
    }

    public FileDownloadingNameTypeStage<FileContentResponse> download(String fileId) {
        return new FileDownloadingNameTypeStage<>(
                this.retrieveFileContentHttpExecutor,
                UploadFileRequest.builder(),
                fileId
        );
    }

    public FileDownloadingStage<FileContentResponse> download(FileResult fileResult) {
        return new FileDownloadingStage<>(
                this.retrieveFileContentHttpExecutor,
                UploadFileRequest.builder(),
                fileResult.id(),
                fileResult.fileName()
        );
    }

    public FileDownloadingStage<FileContentResponse> download(FileResponse fileResponse) {
        return new FileDownloadingStage<>(
                this.retrieveFileContentHttpExecutor,
                UploadFileRequest.builder(),
                fileResponse.id(),
                fileResponse.filename()
        );
    }
}