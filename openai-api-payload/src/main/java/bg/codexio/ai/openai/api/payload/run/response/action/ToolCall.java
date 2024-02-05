package bg.codexio.ai.openai.api.payload.run.response.action;

public record ToolCall(
        String id,
        String type,
        Function function
) {}
