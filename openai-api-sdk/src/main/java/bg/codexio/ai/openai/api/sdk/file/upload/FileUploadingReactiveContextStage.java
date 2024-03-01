package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

import java.io.File;


public class FileUploadingReactiveContextStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O>
        implements RuntimeExecutor {

    FileUploadingReactiveContextStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public ReactiveExecution<O> feed(File file) {
        return this.executor.executeReactive(this.requestBuilder.withFile(file)
                                                                .build());
    }
}