package bg.codexio.ai.openai.api.payload.assistant;

public class Retrieval
        implements AssistantTool {
    private final String type;

    public Retrieval() {
        this.type = "retrieval";
    }

    @Override
    public String getType() {
        return this.type;
    }
}