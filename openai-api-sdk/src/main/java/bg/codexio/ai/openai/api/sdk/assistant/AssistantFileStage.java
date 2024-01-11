package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;
import java.util.Arrays;

public class AssistantFileStage
        extends AssistantConfigurationStage {
    public AssistantFileStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantResponse feed(String... filedIds) {
        return this.httpExecutor.execute(this.requestBuilder.withFileIds(Arrays.asList(filedIds))
                                                            .build());
    }

    public AssistantResponse feed(FileResponse fileResponse) {
        return this.httpExecutor.execute(this.requestBuilder.addFileId(fileResponse.id())
                                                            .build());
    }

    public AssistantResponse feed(File file) {
        return this.httpExecutor.execute(this.requestBuilder.addFileId(this.getFileIdFromUploadedFile(file))
                                                            .build());
    }

    private String getFileIdFromUploadedFile(File file) {
        return Files.defaults()
                    .and()
                    .forAssistants()
                    .feed(file);
    }
}
