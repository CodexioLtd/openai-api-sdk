package bg.codexio.ai.openai.api.payload.purpose;

public class AssistantPurpose
        implements Purpose {

    private final String name;

    public AssistantPurpose() {
        this.name = "assistants";
    }

    @Override
    public String getName() {
        return this.name;
    }
}