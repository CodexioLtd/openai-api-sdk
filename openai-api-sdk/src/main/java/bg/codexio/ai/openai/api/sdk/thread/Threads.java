package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Threads {

    private Threads() {
    }

    public static CreateThreadStage throughHttp(ThreadHttpExecutor httpExecutor) {
        return new CreateThreadStage(
                httpExecutor,
                CreateThreadRequest.builder()
        );
    }

    public static HttpBuilder<CreateThreadStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new CreateThreadStage(
                        new ThreadHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        CreateThreadRequest.builder()
                )

        );
    }

    public static HttpBuilder<CreateThreadStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    public static HttpBuilder<CreateThreadStage> defaults() {
        return autoAuthenticate(Threads::authenticate);
    }
}