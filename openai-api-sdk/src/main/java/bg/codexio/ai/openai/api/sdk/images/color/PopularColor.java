package bg.codexio.ai.openai.api.sdk.images.color;

public enum PopularColor {
    RED(0x00FF0000),
    GREEN(0x00008000),
    BLUE(0x000000FF),
    YELLOW(0x00FFFF00),
    PURPLE(0x00800080),
    CYAN(0x0000FFFF),
    MAGENTA(0x00FF00FF),
    ORANGE(0x00FFA500),
    PINK(0x00FFC0CB),
    BROWN(0x00A52A2A),
    BLACK(0x00000000),
    WHITE(0x00FFFFFF),
    GRAY(0x00808080),
    LIGHT_GRAY(0x00D3D3D3),
    DARK_GRAY(0x00A9A9A9),
    LIME(0x0000FF00),
    MAROON(0x00800000),
    OLIVE(0x00808000),
    NAVY(0x00000080),
    TEAL(0x00008080),
    AQUA(0x0000FFFF),
    SILVER(0x00C0C0C0),
    GOLD(0x00FFD700),
    SKY_BLUE(0x0087CEEB),
    CORAL(0x00FF7F50),
    VIOLET(0x008A2BE2),
    INDIGO(0x004B0082),
    BEIGE(0x00F5F5DC),
    MINT(0x0098FB98),
    LAVENDER(0x00E6E6FA);

    private final int colorHex;

    PopularColor(int colorHex) {
        this.colorHex = colorHex;
    }

    public int val() {
        return colorHex;
    }
}
