package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadSimplified;

import java.io.File;
import java.util.Arrays;

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

    public AssistantAdvancedConfigurationStage feed(File file) {
        return new AssistantAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileId(FileUploadSimplified.simply(file))
        );
    }
}