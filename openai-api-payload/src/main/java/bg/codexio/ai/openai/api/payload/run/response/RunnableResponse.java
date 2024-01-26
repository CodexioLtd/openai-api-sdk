package bg.codexio.ai.openai.api.payload.run.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.assistant.tool.AssistantTool;
import bg.codexio.ai.openai.api.payload.run.response.action.RequiredAction;
import bg.codexio.ai.openai.api.payload.run.response.error.LastError;

import java.util.List;
import java.util.Map;

public record RunnableResponse(
        String id,
        String object,
        Integer createdAt,
        String assistantId,
        String threadId,
        String status,
        RequiredAction requiredAction,
        LastError lastError,
        Integer expiresAt,
        Integer startedAt,
        Integer cancelledAt,
        Integer failedAt,
        Integer completedAt,
        String model,
        String instructions,
        List<AssistantTool> tools,
        List<String> fileIds,
        Map<String, String> metadata
)
        implements Mergeable<RunnableResponse> {
    @Override
    public RunnableResponse merge(RunnableResponse other) {
        if (other == null) {
            return this;
        }

        return null;
    }
}
