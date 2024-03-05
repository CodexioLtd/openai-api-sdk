package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingNameTypeStage;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingRuntimeSelectionStage;
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

    public FileTargetingStage upload() {
        return new FileTargetingStage(
                this.uploadFileHttpExecutor,
                UploadFileRequest.builder()
        );
    }

    public FileDownloadingNameTypeStage download(String fileId) {
        return new FileDownloadingNameTypeStage(
                this.retrieveFileContentHttpExecutor,
                UploadFileRequest.builder(),
                FileDownloadingMeta.builder()
                                   .withFileId(fileId)
        );
    }

    public FileDownloadingRuntimeSelectionStage download(FileResult fileResult) {
        return new FileDownloadingRuntimeSelectionStage(
                this.retrieveFileContentHttpExecutor,
                UploadFileRequest.builder(),
                FileDownloadingMeta.builder()
                                   .withFileId(fileResult.id())
                                   .withFileName(fileResult.fileName())
        );
    }

    public FileDownloadingRuntimeSelectionStage download(FileResponse fileResponse) {
        return new FileDownloadingRuntimeSelectionStage(
                this.retrieveFileContentHttpExecutor,
                UploadFileRequest.builder(),
                FileDownloadingMeta.builder()
                                   .withFileId(fileResponse.id())
                                   .withFileName(fileResponse.filename())
        );
    }
}