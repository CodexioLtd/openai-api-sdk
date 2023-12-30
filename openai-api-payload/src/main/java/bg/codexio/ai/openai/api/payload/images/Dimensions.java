package bg.codexio.ai.openai.api.payload.images;

public enum Dimensions {
    SQUARE_256("256x256"),
    SQUARE_512("512x512"),
    SQUARE_1024("1024x1024"),
    LANDSCAPE_1792("1792x1024"),
    PORTRAIT_1792("1024x1792");

    private final String size;

    Dimensions(String size) {
        this.size = size;
    }

    public String val() {
        return this.size;
    }
}
