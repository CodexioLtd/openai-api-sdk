package bg.codexio.ai.openai.api.sdk;

/**
 * Processing type. Usually defines whether
 * server-sent events (REAL_TIME) to be used
 * or not.
 */
public enum Processing {
    REAL_TIME(true),
    BATCH(false),
    DEFAULT(BATCH.val());

    private final boolean stream;

    Processing(boolean stream) {
        this.stream = stream;
    }

    public boolean val() {
        return this.stream;
    }
}
