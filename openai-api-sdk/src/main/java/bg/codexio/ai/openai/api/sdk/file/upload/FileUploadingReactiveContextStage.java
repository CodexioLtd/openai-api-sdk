package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

import java.io.File;


public class FileUploadingReactiveContextStage
        extends FileUploadingConfigurationStage
        implements RuntimeExecutor {


    FileUploadingReactiveContextStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public ReactiveExecution<FileResponse> feedRaw(File file) {
        return this.executor.executeReactive(this.requestBuilder.withFile(file)
                                                                .build());
    }

    public Mono<String> feed(File file) {
        return this.feedRaw(file)
                   .response()
                   .map(FileResponse::id);
    }
}