package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class ThreadMessageFileStage
        extends ThreadConfigurationStage {

    private final String content;

    ThreadMessageFileStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestBuilder,
            String content
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
        this.content = content;
    }

    public ThreadResponse feed(File file) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(FileSimplified.simply(file)))
                       .orElseGet(() -> this.createWithEmptyContent(FileSimplified.simply(file)));
    }

    public ThreadResponse feed(FileResponse fileResponse) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(fileResponse.id()))
                       .orElseGet(() -> this.createWithEmptyContent(fileResponse.id()));
    }

    public ThreadResponse feed(String... fileId) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(fileId))
                       .orElseGet(() -> this.createWithEmptyContent(fileId));
    }

    public ThreadAdvancedConfiguration supply(File file) {
        return new ThreadAdvancedConfiguration(
                this.httpExecutor,
                this.buildWithoutContent(FileSimplified.simply(file))
        );
    }

    public ThreadAdvancedConfiguration supply(FileResponse fileResponse) {
        return new ThreadAdvancedConfiguration(
                this.httpExecutor,
                this.buildWithoutContent(fileResponse.id())
        );
    }

    public ThreadAdvancedConfiguration supply(String... fileId) {
        return new ThreadAdvancedConfiguration(
                this.httpExecutor,
                this.buildWithoutContent(fileId)
        );
    }

    private ThreadResponse create(String fileId) {
        return this.httpExecutor.execute(this.requestBuilder.addMessage(MessageRequest.builder()
                                                                                      .addFileIDs(fileId)
                                                                                      .withRole("user")
                                                                                      .withContent(this.content)
                                                                                      .build())
                                                            .build());
    }

    public ThreadResponse createWithEmptyContent(String fileId) {
        return this.httpExecutor.execute(this.buildWithoutContent(fileId)
                                             .build());
    }

    public ThreadResponse create(String... fileId) {
        return this.httpExecutor.execute(this.requestBuilder.addMessage(MessageRequest.builder()
                                                                                      .withFileIds(Arrays.asList(fileId))
                                                                                      .withRole("user")
                                                                                      .withContent(this.content)
                                                                                      .build())
                                                            .build());
    }

    public ThreadResponse createWithEmptyContent(String... fileId) {
        return this.httpExecutor.execute(this.buildWithoutContent(fileId)
                                             .build());
    }

    public CreateThreadRequest.Builder buildWithoutContent(String fileId) {
        return this.requestBuilder.addMessage(MessageRequest.builder()
                                                            .addFileIDs(fileId)
                                                            .withRole("user")
                                                            .build());
    }

    public CreateThreadRequest.Builder buildWithoutContent(String... fileId) {
        return this.requestBuilder.addMessage(MessageRequest.builder()
                                                            .withFileIds(Arrays.asList(fileId))
                                                            .withRole("user")
                                                            .build());
    }
}
