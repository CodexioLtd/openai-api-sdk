package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingNameTypeStage;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingRuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadingTargetingStage;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Files {

    private Files() {
    }

    public static FileUploadingTargetingStage throughHttp(UploadFileHttpExecutor httpExecutor) {
        return new FileUploadingTargetingStage(
                httpExecutor,
                UploadFileRequest.builder()
        );
    }

    public static FileDownloadingNameTypeStage throughHttp(
            RetrieveFileContentHttpExecutor httpExecutor,
            String fileId
    ) {
        return new FileDownloadingNameTypeStage(
                httpExecutor,
                FileDownloadingMeta.builder()
                                   .withFileId(fileId)
        );
    }

    public static FileDownloadingRuntimeSelectionStage throughHttp(
            RetrieveFileContentHttpExecutor httpExecutor,
            FileResponse fileResponse
    ) {
        return new FileDownloadingRuntimeSelectionStage(
                httpExecutor,
                FileDownloadingMeta.builder()
                                   .withFileId(fileResponse.id())
                                   .withFileName(fileResponse.filename())
        );
    }

    public static HttpBuilder<FileActionTypeStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new FileActionTypeStage(
                        new UploadFileHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        new RetrieveFileContentHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        )
                )

        );
    }

    public static HttpBuilder<FileActionTypeStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    public static HttpBuilder<FileActionTypeStage> defaults() {
        return autoAuthenticate(Files::authenticate);
    }
}