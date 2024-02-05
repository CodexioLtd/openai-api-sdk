package bg.codexio.ai.openai.api.payload.run.response.action;

import java.util.List;

public record SubmitToolOutput(
        List<ToolCall> toolCalls
) {}