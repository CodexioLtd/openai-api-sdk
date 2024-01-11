package bg.codexio.ai.openai.api.payload.assistant;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@FunctionalInterface
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
public interface AssistantTool {
    String type();
}