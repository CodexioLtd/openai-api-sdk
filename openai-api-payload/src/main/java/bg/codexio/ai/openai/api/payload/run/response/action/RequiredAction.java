package bg.codexio.ai.openai.api.payload.run.response.action;

import java.util.Objects;

public record RequiredAction(
        String type,
        SubmitToolOutput submitToolOutput
) {
    @Override
    public String type() {
        return Objects.requireNonNullElse(
                this.type,
                "submit_tool_outputs"
        );
    }
}