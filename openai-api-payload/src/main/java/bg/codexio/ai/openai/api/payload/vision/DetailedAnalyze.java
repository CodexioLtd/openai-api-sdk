package bg.codexio.ai.openai.api.payload.vision;

public enum DetailedAnalyze {
    HIGH("high"),
    LOW("low");

    private final String detail;

    DetailedAnalyze(String detail) {
        this.detail = detail;
    }

    public String val() {
        return this.detail;
    }
}
