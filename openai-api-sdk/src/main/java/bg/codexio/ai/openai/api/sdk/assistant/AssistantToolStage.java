package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.AssistantTool;
import bg.codexio.ai.openai.api.sdk.ObjectMapperSubtypesRegistrationUtils;

import java.util.Arrays;

public class AssistantToolStage
        extends AssistantConfigurationStage {

    AssistantToolStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantNameStage from(AssistantTool... assistantTool) {
        ObjectMapperSubtypesRegistrationUtils.registerAssistantTools(
                this.httpExecutor,
                Arrays.asList(assistantTool)
        );

        return new AssistantNameStage(
                this.httpExecutor,
                this.requestBuilder.withTools(Arrays.asList(assistantTool))
        );
    }
}