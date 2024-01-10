package bg.codexio.ai.openai.api.payload.assistant;

public class CodeInterpreter
        implements AssistantTool {
    private final String type;

    public CodeInterpreter() {
        this.type = "code_interpreter";
    }

    @Override
    public String getType() {
        return this.type;
    }
}
