package bg.codexio.ai.openai.api.payload.images;

public enum Style {
    HYPER_REAL("vivid"),
    NATURAL("natural");

    private final String value;

    Style(String value) {
        this.value = value;
    }

    public String val() {
        return this.value;
    }
}
