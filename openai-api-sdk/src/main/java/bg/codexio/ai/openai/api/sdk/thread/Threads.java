package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Threads {

    private Threads() {
    }

    public static ThreadCreationStage<ThreadCreationRequest> throughHttp(CreateThreadHttpExecutor httpExecutor) {
        return new ThreadCreationStage<>(
                httpExecutor,
                ThreadRequestBuilder.builder()
        );
    }

    public static ThreadModificationStage<ThreadModificationRequest> throughHttp(
            ModifyThreadHttpExecutor httpExecutor,
            String threadId
    ) {
        return new ThreadModificationStage<>(
                httpExecutor,
                ThreadRequestBuilder.builder(),
                threadId
        );
    }

    public static HttpBuilder<ThreadActionTypeStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new ThreadActionTypeStage(
                        new CreateThreadHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        new ModifyThreadHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        )
                )
        );
    }

    public static HttpBuilder<ThreadActionTypeStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    public static HttpBuilder<ThreadActionTypeStage> defaults() {
        return autoAuthenticate(Threads::authenticate);
    }
}