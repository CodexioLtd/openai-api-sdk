package bg.codexio.ai.openai.api.payload.images;

import java.util.Arrays;

public enum Format {
    BASE_64("b64_json"),
    URL("url");

    private final String value;

    Format(String value) {
        this.value = value;
    }

    public static Format fromVal(String text) {
        return Arrays.stream(Format.values())
                     .filter(format -> format.value.equalsIgnoreCase(text))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(
                             "No constant with text " + text + " found"));
    }

    public String val() {
        return this.value;
    }
}
