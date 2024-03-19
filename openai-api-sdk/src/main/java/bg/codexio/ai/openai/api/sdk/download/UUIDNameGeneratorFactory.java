package bg.codexio.ai.openai.api.sdk.download;

public class UUIDNameGeneratorFactory
        implements UniqueFileNameGeneratorFactory {
    @Override
    public UniqueFileNameGenerator create() {
        return UUIDNameGenerator.getInstance();
    }
}