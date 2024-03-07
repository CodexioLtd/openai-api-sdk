package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadSimplified;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

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


    public ThreadRuntimeSelectionStage feed(File file) {
        return Optional.ofNullable(content)
                       .map(c -> this.create(FileUploadSimplified.simply(file)))
                       .orElseGet(() -> this.createWithEmptyContent(FileUploadSimplified.simply(file)));
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

    public ThreadAdvancedConfigurationStage attach(File file) {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.buildWithoutContent(FileUploadSimplified.simply(file))
        );
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