package bg.codexio.ai.openai.api.sdk.download.name;

@FunctionalInterface
public interface UniqueFileNameGenerator {
    String generateRandomNamePrefix();
}