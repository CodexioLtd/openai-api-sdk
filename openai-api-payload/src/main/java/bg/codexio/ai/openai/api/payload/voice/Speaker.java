package bg.codexio.ai.openai.api.payload.voice;

public enum Speaker {
    ALLOY("alloy"),
    ECHO("echo"),
    FABLE("fable"),
    ONYX("onyx"),
    NOVA("nova"),
    SHIMMER("shimmer");

    private final String name;

    Speaker(String name) {
        this.name = name;
    }

    public String val() {
        return this.name;
    }
}
