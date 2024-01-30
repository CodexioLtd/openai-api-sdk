package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Files {

    private Files() {
    }

    public static FileTargetingStage<FileResponse> throughHttp(UploadFileHttpExecutor httpExecutor) {
        return new FileTargetingStage<>(
                httpExecutor,
                UploadFileRequest.builder()
        );
    }

    public static FileDownloadingStage<FileContentResponse> throughHttp(
            RetrieveFileContentHttpExecutor httpExecutor,
            String fileId
    ) {
        return new FileDownloadingStage<>(
                httpExecutor,
                UploadFileRequest.builder(),
                fileId,
                null
        );
    }

    public static FileDownloadingStage<FileContentResponse> throughHttp(
            RetrieveFileContentHttpExecutor httpExecutor,
            FileResponse fileResponse
    ) {
        return new FileDownloadingStage<>(
                httpExecutor,
                UploadFileRequest.builder(),
                fileResponse.id(),
                fileResponse.filename()
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