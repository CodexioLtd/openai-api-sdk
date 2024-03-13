package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.thread.constant.ThreadDefaultValuesConstants.MESSAGE_SENDER_ROLE;

public class ThreadMessageFileStage
        extends ThreadConfigurationStage {

    private final String content;

    ThreadMessageFileStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder,
            String content
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
        this.content = content;
    }


    public ThreadRuntimeSelectionStage feedImmediate(File file) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(FileImmediateUploadSimplified.simply(file)))
                       .orElseGet(() -> this.createWithEmptyContent(FileImmediateUploadSimplified.simply(file)));
    }

    public void feedAsync(
            File file,
            Consumer<ThreadRuntimeSelectionStage> consumer
    ) {
        Optional.ofNullable(content)
                .ifPresentOrElse(
                        c -> FileAsyncUploadSimplified.simply(
                                file,
                                fileId -> consumer.accept(this.create(fileId))
                        ),
                        () -> FileAsyncUploadSimplified.simply(
                                file,
                                fileId -> consumer.accept(this.createWithEmptyContent(fileId))
                        )
                );

    }

    public Mono<ThreadRuntimeSelectionStage> feedReactive(File file) {
        return Optional.ofNullable(content)
                       .map(c -> FileReactiveUploadSimplified.simply(file)
                                                             .flatMap(fileId -> Mono.justOrEmpty(this.create(fileId))))
                       .orElseGet(() -> FileReactiveUploadSimplified.simply(file)
                                                                    .flatMap(fileId -> Mono.just(this.createWithEmptyContent(fileId))));
    }

    public ThreadRuntimeSelectionStage feed(FileResponse fileResponse) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(fileResponse.id()))
                       .orElseGet(() -> this.createWithEmptyContent(fileResponse.id()));
    }

    public ThreadRuntimeSelectionStage feed(String... fileId) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(fileId))
                       .orElseGet(() -> this.createWithEmptyContent(fileId));
    }

    public ThreadAdvancedConfigurationStage attachImmediate(File file) {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.buildWithoutContent(FileImmediateUploadSimplified.simply(file))
        );
    }

    public void attachAsync(
            File file,
            Consumer<ThreadAdvancedConfigurationStage> consumer
    ) {
        FileAsyncUploadSimplified.simply(
                file,
                fileId -> consumer.accept(new ThreadAdvancedConfigurationStage(
                        this.httpExecutor,
                        this.buildWithoutContent(fileId)
                ))
        );
    }

    public Mono<ThreadAdvancedConfigurationStage> attachReactive(File file) {
        return FileReactiveUploadSimplified.simply(file)
                                           .flatMap(fileId -> Mono.justOrEmpty(new ThreadAdvancedConfigurationStage(
                                                   this.httpExecutor,
                                                   this.buildWithoutContent(fileId)
                                           )));
    }

    public ThreadAdvancedConfigurationStage attach(FileResponse fileResponse) {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.buildWithoutContent(fileResponse.id())
        );
    }

    public ThreadAdvancedConfigurationStage attach(String... fileId) {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.buildWithoutContent(fileId)
        );
    }

    private ThreadRuntimeSelectionStage createWithEmptyContent(String fileId) {
        return this.initializeRuntimeSelection(this.buildWithoutContent(fileId));
    }

    private ThreadRuntimeSelectionStage create(String... fileId) {
        return this.initializeRuntimeSelection(this.requestBuilder.addMessage(MessageRequest.builder()
                                                                                            .withFileIds(Arrays.asList(fileId))
                                                                                            .withRole(MESSAGE_SENDER_ROLE)
                                                                                            .withContent(this.content)
                                                                                            .build()));
    }

    private ThreadRuntimeSelectionStage createWithEmptyContent(String... fileId) {
        return this.initializeRuntimeSelection(this.buildWithoutContent(fileId));
    }

    private ThreadCreationRequest.Builder buildWithoutContent(String fileId) {
        return this.requestBuilder.addMessage(MessageRequest.builder()
                                                            .addFileIDs(fileId)
                                                            .withRole(MESSAGE_SENDER_ROLE)
                                                            .build());
    }

    private ThreadCreationRequest.Builder buildWithoutContent(String... fileId) {
        return this.requestBuilder.addMessage(MessageRequest.builder()
                                                            .withFileIds(Arrays.asList(fileId))
                                                            .withRole(MESSAGE_SENDER_ROLE)
                                                            .build());
    }

    private ThreadRuntimeSelectionStage create(String fileId) {
        return this.initializeRuntimeSelection(this.requestBuilder.addMessage(MessageRequest.builder()
                                                                                            .addFileIDs(fileId)
                                                                                            .withRole(MESSAGE_SENDER_ROLE)
                                                                                            .withContent(this.content)
                                                                                            .build()));
    }

    private ThreadRuntimeSelectionStage initializeRuntimeSelection(ThreadCreationRequest.Builder requestBuilder) {
        return new ThreadRuntimeSelectionStage(
                this.httpExecutor,
                requestBuilder
        );
    }
}