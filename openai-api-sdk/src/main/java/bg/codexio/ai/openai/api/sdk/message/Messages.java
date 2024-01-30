package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Messages {

    private Messages() {
    }

    public static MessageContentStage<MessageResponse> throughHttp(
            MessageHttpExecutor httpExecutor,
            String threadId
    ) {
        return new MessageContentStage<>(
                httpExecutor,
                MessageRequest.builder(),
                threadId
        );
    }

    public static MessageContentStage<MessageResponse> throughHttp(
            MessageHttpExecutor httpExecutor,
            ThreadResponse threadResponse
    ) {
        return new MessageContentStage<>(
                httpExecutor,
                MessageRequest.builder(),
                threadResponse.id()
        );
    }

    public static MessageAnswerStage<ListMessagesResponse> throughHttp(
            RetrieveListMessagesHttpExecutor httpExecutor,
            String threadId
    ) {
        return new MessageAnswerStage<>(
                httpExecutor,
                MessageRequest.builder(),
                threadId
        );
    }

    public static MessageAnswerStage<ListMessagesResponse> throughHttp(
            RetrieveListMessagesHttpExecutor httpExecutor,
            ThreadResponse threadResponse
    ) {
        return new MessageAnswerStage<>(
                httpExecutor,
                MessageRequest.builder(),
                threadResponse.id()
        );
    }

    public static HttpBuilder<MessageActionTypeStage> authenticate(
            HttpExecutorContext context,
            String threadId
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new MessageActionTypeStage(
                        new MessageHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        new RetrieveListMessagesHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        threadId
                )
        );
    }

    public static HttpBuilder<MessageActionTypeStage> authenticate(
            HttpExecutorContext context,
            ThreadResponse thread
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new MessageActionTypeStage(
                        new MessageHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        new RetrieveListMessagesHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        thread.id()
                )
        );
    }

    public static HttpBuilder<MessageActionTypeStage> authenticate(
            SdkAuth auth,
            String threadId
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                threadId
        );
    }

    public static HttpBuilder<MessageActionTypeStage> authenticate(
            SdkAuth auth,
            ThreadResponse thread
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                thread.id()
        );
    }

    public static HttpBuilder<MessageActionTypeStage> defaults(String threadId) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                threadId
        ));
    }

    public static HttpBuilder<MessageActionTypeStage> defaults(ThreadResponse thread) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                thread.id()
        ));
    }
}