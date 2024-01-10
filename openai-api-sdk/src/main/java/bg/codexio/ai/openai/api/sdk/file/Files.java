package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Files {

    private Files() {
    }

    public static TargetingStage throughHttp(UploadFileHttpExecutor httpExecutor) {
        return new TargetingStage(
                httpExecutor,
                UploadFileRequest.builder()
        );
    }

    public static HttpBuilder<TargetingStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new TargetingStage(
                        new UploadFileHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        UploadFileRequest.builder()
                )

        );
    }

    public static HttpBuilder<TargetingStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    public static HttpBuilder<TargetingStage> defaults() {
        return autoAuthenticate(Files::authenticate);
    }
}