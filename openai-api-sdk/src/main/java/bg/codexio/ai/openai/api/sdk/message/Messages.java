package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
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

    public static <O extends Mergeable<O>> HttpBuilder<MessageActionTypeStage<O>> authenticate(
            HttpExecutorContext context,
            String threadId
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new MessageActionTypeStage<O>(
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

    public static <O extends Mergeable<O>> HttpBuilder<MessageActionTypeStage<O>> authenticate(
            HttpExecutorContext context,
            ThreadResponse thread
    ) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new MessageActionTypeStage<O>(
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

    public static <O extends Mergeable<O>> HttpBuilder<MessageActionTypeStage<O>> authenticate(
            SdkAuth auth,
            String threadId
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                threadId
        );
    }

    public static <O extends Mergeable<O>> HttpBuilder<MessageActionTypeStage<O>> authenticate(
            SdkAuth auth,
            ThreadResponse thread
    ) {
        return authenticate(
                new HttpExecutorContext(auth.credentials()),
                thread.id()
        );
    }

    public static <O extends Mergeable<O>> HttpBuilder<MessageActionTypeStage<O>> defaults(String threadId) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                threadId
        ));
    }

    public static <O extends Mergeable<O>> HttpBuilder<MessageActionTypeStage<O>> defaults(ThreadResponse thread) {
        return autoAuthenticate(auth -> authenticate(
                auth,
                thread.id()
        ));
    }
}