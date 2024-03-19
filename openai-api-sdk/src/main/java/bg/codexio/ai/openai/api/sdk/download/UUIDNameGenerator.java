package bg.codexio.ai.openai.api.sdk.download;

import java.util.UUID;

public class UUIDNameGenerator
        implements UniqueFileNameGenerator {

    private static UUIDNameGenerator instance;

    private UUIDNameGenerator() {
    }

    public static synchronized UUIDNameGenerator getInstance() {
        if (instance == null) {
            instance = new UUIDNameGenerator();
        }

        return instance;
    }

    @Override
    public String generateRandomNamePrefix() {
        return UUID.randomUUID()
                   .toString();
    }
}