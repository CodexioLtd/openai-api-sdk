package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.io.File;

public class FileUploadingStage
        extends FileConfigurationStage {

    FileUploadingStage(
            UploadFileHttpExecutor executor,
            UploadFileRequest.Builder requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    public FileResponse feedRaw(File file) {
        return this.executor.execute(this.requestContext.withFile(file)
                                                        .build());
    }

    public String feed(File file) {
        return this.feedRaw(file)
                   .id();
    }
}
