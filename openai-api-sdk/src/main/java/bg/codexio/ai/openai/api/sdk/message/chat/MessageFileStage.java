package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;

public class MessageFileStage
        extends MessageConfigurationStage {

    public MessageFileStage(
            MessageHttpExecutor httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public MessageAdvancedConfigurationStage feed(String... fileId) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withFileIds(Arrays.asList(fileId)),
                this.threadId
        );
    }

    public MessageAdvancedConfigurationStage feed(FileResponse fileResponse) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(fileResponse.id()),
                this.threadId
        );
    }

    public MessageAdvancedConfigurationStage feedImmediate(File file) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(FileImmediateUploadSimplified.simply(file)),
                this.threadId
        );
    }

    public void feedAsync(
            File file,
            Consumer<MessageAdvancedConfigurationStage> consumer
    ) {
        FileAsyncUploadSimplified.simply(
                file,
                fileId -> consumer.accept(new MessageAdvancedConfigurationStage(
                        this.httpExecutor,
                        this.requestBuilder.addFileIDs(fileId),
                        this.threadId
                ))
        );
    }

    public Mono<MessageAdvancedConfigurationStage> feedReactive(File file) {
        return FileReactiveUploadSimplified.simply(file)
                                           .flatMap(fileId -> Mono.justOrEmpty(new MessageAdvancedConfigurationStage(
                                                   this.httpExecutor,
                                                   this.requestBuilder.addFileIDs(fileId),
                                                   this.threadId
                                           )));
    }
}