package bg.codexio.ai.openai.api.models;

public abstract class ModelTypeAbstract
        implements ModelType {

    private final String name;

    protected ModelTypeAbstract(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
