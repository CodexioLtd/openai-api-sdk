package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.AssistantTool;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.util.Arrays;

public class ToolStage
        extends AssistantConfigurationStage {
    ToolStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantNameStage from(AssistantTool... assistantTool) {
        for (var tool : assistantTool) {
            this.httpExecutor.configureMappingExternally(objectMapper -> objectMapper.registerSubtypes(new NamedType(
                    tool.getClass(),
                    tool.type()
            )));
        }

        return new AssistantNameStage(
                this.httpExecutor,
                this.requestBuilder.withTools(Arrays.asList(assistantTool))
        );
    }
}