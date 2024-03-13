package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;

public class AssistantFileStage
        extends AssistantConfigurationStage {

    AssistantFileStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantAdvancedConfigurationStage feed(String... filedIds) {
        return new AssistantAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withFileIds(Arrays.asList(filedIds))
        );
    }

    public AssistantAdvancedConfigurationStage feed(FileResponse fileResponse) {
        return new AssistantAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileId(fileResponse.id())
        );
    }

    public AssistantAdvancedConfigurationStage feedImmediate(File file) {
        return new AssistantAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileId(FileImmediateUploadSimplified.simply(file))
        );
    }

    public void feedAsync(
            File file,
            Consumer<AssistantAdvancedConfigurationStage> consumer
    ) {
        FileAsyncUploadSimplified.simply(
                file,
                id -> consumer.accept(new AssistantAdvancedConfigurationStage(
                        this.httpExecutor,
                        this.requestBuilder.addFileId(id)
                ))
        );
    }

    public Mono<AssistantAdvancedConfigurationStage> feedReactive(File file) {
        return FileReactiveUploadSimplified.simply(file)
                                           .flatMap(fileId -> Mono.justOrEmpty(new AssistantAdvancedConfigurationStage(
                                                   this.httpExecutor,
                                                   this.requestBuilder.addFileId(fileId)
                                           )));
    }
}