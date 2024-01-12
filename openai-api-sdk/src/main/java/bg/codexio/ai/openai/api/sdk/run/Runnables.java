package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Runnables {

    private Runnables() {
    }

    public static RunnableInitializationStage throughHttp(
            RunnableHttpExecutor httpExecutor,
            String threadId
    ) {
        return new RunnableInitializationStage(
                httpExecutor,
                RunnableRequest.builder(),
                threadId
        );
    }

    public static RunnableInitializationStage throughHttp(
            RunnableHttpExecutor httpExecutor,
            ThreadResponse thread
    ) {
        return new RunnableInitializationStage(
                httpExecutor,
                RunnableRequest.builder(),
                thread.id()
        );
    }

    public static HttpBuilder<RunnableInitializationStage> authenticate(
            HttpExecutorContext context,
            String threadId
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new RunnableInitializationStage(
                        new RunnableHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        RunnableRequest.builder(),
                        threadId
                )

        );
    }

    public static HttpBuilder<RunnableInitializationStage> authenticate(
            HttpExecutorContext context,
            ThreadResponse thread
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new RunnableInitializationStage(
                        new RunnableHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        RunnableRequest.builder(),
                        thread.id()
                )

        );
    }

    public static HttpBuilder<RunnableInitializationStage> authenticate(
            SdkAuth auth,
            String threadId
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                threadId
        );
    }

    public static HttpBuilder<RunnableInitializationStage> authenticate(
            SdkAuth auth,
            ThreadResponse thread
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                thread.id()
        );
    }

    public static HttpBuilder<RunnableInitializationStage> defaults(String threadId) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                threadId
        ));
    }

    public static HttpBuilder<RunnableInitializationStage> defaults(ThreadResponse thread) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                thread.id()
        ));
    }
}
