package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;

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

    public AdvancedConfigurationStage feed(String... filedIds) {
        return new AdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withFileIds(Arrays.asList(filedIds))
        );
    }

    public AdvancedConfigurationStage feed(FileResponse fileResponse) {
        return new AdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileId(fileResponse.id())
        );
    }

    public AdvancedConfigurationStage feed(File file) {
        return new AdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileId(FileSimplified.simply(file))
        );
    }
}