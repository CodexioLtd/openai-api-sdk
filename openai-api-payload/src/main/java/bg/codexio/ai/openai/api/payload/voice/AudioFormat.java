package bg.codexio.ai.openai.api.payload.voice;

public enum AudioFormat {
    MP3("mp3"),
    OPUS("opus"),
    AAC("acc"),
    FLAC("flac");

    private final String format;

    AudioFormat(String format) {
        this.format = format;
    }

    public String val() {
        return this.format;
    }
}
