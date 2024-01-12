package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Messages {
    private Messages() {
    }

    public static MessageContentStage throughHttp(
            MessageHttpExecutor httpExecutor,
            String threadId
    ) {
        return new MessageContentStage(
                httpExecutor,
                MessageRequest.builder(),
                threadId
        );
    }

    public static MessageContentStage throughHttp(
            MessageHttpExecutor httpExecutor,
            ThreadResponse thread
    ) {
        return new MessageContentStage(
                httpExecutor,
                MessageRequest.builder(),
                thread.id()
        );
    }

    public static HttpBuilder<MessageContentStage> authenticate(
            HttpExecutorContext context,
            String threadId
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new MessageContentStage(
                        new MessageHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        MessageRequest.builder(),
                        threadId
                )

        );
    }

    public static HttpBuilder<MessageContentStage> authenticate(
            HttpExecutorContext context,
            ThreadResponse thread
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new MessageContentStage(
                        new MessageHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        MessageRequest.builder(),
                        thread.id()
                )

        );
    }

    public static HttpBuilder<MessageContentStage> authenticate(
            SdkAuth auth,
            String threadId
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                threadId
        );
    }

    public static HttpBuilder<MessageContentStage> authenticate(
            SdkAuth auth,
            ThreadResponse thread
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                thread.id()
        );
    }

    public static HttpBuilder<MessageContentStage> defaults(String threadId) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                threadId
        ));
    }

    public static HttpBuilder<MessageContentStage> defaults(ThreadResponse thread) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                thread.id()
        ));
    }
}