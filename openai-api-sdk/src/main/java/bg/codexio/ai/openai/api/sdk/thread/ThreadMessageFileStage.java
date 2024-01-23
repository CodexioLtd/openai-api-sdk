package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class ThreadMessageFileStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {

    private final String content;

    ThreadMessageFileStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder,
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

    public ThreadAdvancedConfigurationStage<R> attach(File file) {
        return new ThreadAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.buildWithoutContent(FileSimplified.simply(file))
        );
    }

    public ThreadAdvancedConfigurationStage<R> attach(FileResponse fileResponse) {
        return new ThreadAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.buildWithoutContent(fileResponse.id())
        );
    }

    public ThreadAdvancedConfigurationStage<R> attach(String... fileId) {
        return new ThreadAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.buildWithoutContent(fileId)
        );
    }

    private ThreadResponse createWithEmptyContent(String fileId) {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.buildWithoutContent(fileId)
                                                                       .build()));
    }

    private ThreadResponse create(String... fileId) {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.requestBuilder.addMessage(MessageRequest.builder()
                                                                                                                .withFileIds(Arrays.asList(fileId))
                                                                                                                .withRole("user")
                                                                                                                .withContent(this.content)
                                                                                                                .build())
                                                                                      .build()));
    }

    private ThreadResponse createWithEmptyContent(String... fileId) {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.buildWithoutContent(fileId)
                                                                       .build()));
    }

    private ThreadRequestBuilder<R> buildWithoutContent(String fileId) {
        return this.requestBuilder.addMessage(MessageRequest.builder()
                                                            .addFileIDs(fileId)
                                                            .withRole("user")
                                                            .build());
    }

    private ThreadRequestBuilder<R> buildWithoutContent(String... fileId) {
        return this.requestBuilder.addMessage(MessageRequest.builder()
                                                            .withFileIds(Arrays.asList(fileId))
                                                            .withRole("user")
                                                            .build());
    }

    private ThreadResponse create(String fileId) {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.requestBuilder.addMessage(MessageRequest.builder()
                                                                                                                .addFileIDs(fileId)
                                                                                                                .withRole("user")
                                                                                                                .withContent(this.content)
                                                                                                                .build())
                                                                                      .build()));
    }
}