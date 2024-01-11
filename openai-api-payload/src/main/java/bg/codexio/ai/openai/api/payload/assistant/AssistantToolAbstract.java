package bg.codexio.ai.openai.api.payload.assistant;

public abstract class AssistantToolAbstract
        implements AssistantTool {

    private final String type;

    protected AssistantToolAbstract(String type) {
        this.type = type;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type();
    }
}