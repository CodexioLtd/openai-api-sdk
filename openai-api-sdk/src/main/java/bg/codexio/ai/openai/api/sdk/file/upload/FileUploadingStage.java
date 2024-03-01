package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.io.File;

public class FileUploadingStage {

    private final UploadFileHttpExecutor executor;
    private final UploadFileRequest.Builder requestBuilder;

    public FileUploadingStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestContext
    ) {
        this.executor = executor;
        this.requestBuilder = requestContext;
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