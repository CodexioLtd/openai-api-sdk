package bg.codexio.ai.openai.api.payload;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MetadataUtils {
    public static Map<String, String> addMetadata(
            Map<String, String> originalMetadata,
            String... metadata
    ) {
        if (metadata.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Metadata needs to contain at " + "least 2 arguments.");
        }

        var metadataMap = Objects.requireNonNullElse(
                originalMetadata,
                new HashMap<String, String>()
        );
        for (int i = 0; i < metadata.length - 1; i += 2) {
            metadataMap.put(
                    metadata[i],
                    metadata[i + 1]
            );
        }

        return metadataMap;
    }
}