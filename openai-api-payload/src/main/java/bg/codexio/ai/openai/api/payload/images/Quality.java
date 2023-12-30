package bg.codexio.ai.openai.api.payload.images;

public enum Quality {
    STANDARD("standard"),
    HIGH_QUALITY("hd");

    private final String value;

    Quality(String value) {
        this.value = value;
    }

    public String val() {
        return this.value;
    }
}
