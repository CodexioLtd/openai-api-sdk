package bg.codexio.ai.openai.api.sdk.images.tolerance;

public enum ColorDeviation {
    THE_SAME(0.0),
    SMALL_DIFFERENCE(0.05),
    BALANCED_TOLERANCE(0.1),
    VISIBLE_DIFFERENCE(0.15),
    TWENTY_PERCENT(0.2),
    THIRTY_PERCENT(0.3),
    HALF(0.5),
    ALMOST_THE_WHOLE_PICTURE(0.75),
    EVERYTHING(1.0);

    private final double tolerance;

    ColorDeviation(double tolerance) {
        this.tolerance = tolerance;
    }

    public double val() {
        return this.tolerance;
    }
}
