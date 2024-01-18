package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;

import java.util.Arrays;

public class ThreadMessageContentStage
        extends CreateThreadStage {

    ThreadMessageContentStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestContext
    ) {
        super(
                httpExecutor,
                requestContext
        );
    }

    public ThreadAdvancedConfiguration startWith(String... content) {
        return new ThreadAdvancedConfiguration(
                this.httpExecutor,
                this.requestBuilder.withMessages(Arrays.stream(content)
                                                       .map(message -> MessageRequest.builder()
                                                                                     .withContent(message)
                                                                                     .withRole("user")
                                                                                     .build())
                                                       .toList())
        );
    }

    public ThreadMessageFileStage startWith(String content) {
        return new ThreadMessageFileStage(
                this.httpExecutor,
                this.requestBuilder,
                content
        );
    }
}