package bg.codexio.ai.openai.api.payload.assistant.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.assistant.AssistantTool;

import java.util.List;
import java.util.Map;

public record AssistantResponse(
        String id,
        String object,
        Integer createdAt,
        String name,
        String description,
        String model,
        String instructions,
        List<AssistantTool> tools,
        List<String> fileIds,
        Map<String, String> metadata
)
        implements Mergeable<AssistantResponse> {

    @Override
    public AssistantResponse merge(AssistantResponse other) {
        if (other == null) {
            return this;
        }

        return other;
    }
}
