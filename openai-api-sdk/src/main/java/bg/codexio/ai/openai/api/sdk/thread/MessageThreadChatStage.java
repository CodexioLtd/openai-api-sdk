package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;

import java.io.File;
import java.util.Arrays;

public class MessageThreadChatStage
        extends CreateThreadStage {
    protected MessageThreadChatStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestContext
    ) {
        super(
                httpExecutor,
                requestContext
        );
    }

    public ThreadResponse startWith(String... content) {
        return this.httpExecutor.execute(this.requestContext.withMessages(Arrays.stream(content)
                                                                                .map(message -> MessageRequest.builder()
                                                                                                              .withContent(message)
                                                                                                              .withRole("user")
                                                                                                              .build())
                                                                                .toList())
                                                            .build());
    }

    public MessageThreadChatStage startWithToFile(String... content) {
        return new MessageThreadChatStage(
                this.httpExecutor,
                this.requestContext.withMessages(Arrays.stream(content)
                                                       .map(message -> MessageRequest.builder()
                                                                                     .withContent(message)
                                                                                     .withRole("user")
                                                                                     .build())
                                                       .toList())
        );
    }

    public ThreadResponse feed(File file) {
        return this.httpExecutor.execute(this.requestContext.addMessage(MessageRequest.builder()
                                                                                      .addFileIDs(FileSimplified.simply(file))
                                                                                      .withRole("user")
                                                                                      .build())
                                                            .build());
    }

    public ThreadResponse feed(String... fileId) {
        return this.httpExecutor.execute(this.requestContext.addMessage(MessageRequest.builder()
                                                                                      .withFileIds(Arrays.asList(fileId))
                                                                                      .withRole("user")
                                                                                      .build())
                                                            .build());
    }

    public ThreadResponse feed(FileResponse fileResponse) {
        return this.httpExecutor.execute(this.requestContext.addMessage(MessageRequest.builder()
                                                                                      .addFileIDs(fileResponse.id())
                                                                                      .withRole("user")
                                                                                      .build())
                                                            .build());
    }
}