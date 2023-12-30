package bg.codexio.ai.openai.api.payload.voice;

public enum Speed {
    VERY_SLOW(0.25),
    HALF_SLOW(0.50),
    NORMAL(1.00),
    HALF_FASTER(1.50),
    TWICE_FASTER(2.00),
    VERY_FAST(4.00);

    private final double value;

    Speed(double value) {
        this.value = value;
    }

    public double val() {
        return this.value;
    }
}
