package bg.codexio.ai.openai.api.payload.file.purpose;

public abstract class PurposeAbstract
        implements Purpose {

    private final String name;

    protected PurposeAbstract(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }
}