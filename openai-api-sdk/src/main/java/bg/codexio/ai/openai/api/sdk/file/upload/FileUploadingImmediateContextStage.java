package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.io.File;

public class FileUploadingImmediateContextStage
        extends FileUploadingConfigurationStage
        implements RuntimeExecutor {

    public FileUploadingImmediateContextStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    public FileResponse feedRaw(File file) {
        return this.executor.execute(this.requestBuilder.withFile(file)
                                                        .build());
    }

    public String feed(File file) {
        return this.feedRaw(file)
                   .id();
    }
}