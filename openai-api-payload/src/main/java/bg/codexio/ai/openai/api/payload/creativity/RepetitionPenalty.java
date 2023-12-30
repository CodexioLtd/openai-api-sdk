package bg.codexio.ai.openai.api.payload.creativity;

public enum RepetitionPenalty {
    REPEAT_A_LOT(0.0),
    REUSE_VOCABULARY(0.1),
    SLIGHT_REPETITION_DISCOURAGE(0.2),
    CONSTRAINTS_GOT_VISIBLE(0.3),
    ALMOST_GOOD(0.4),
    REDUCED_REDUNDANCY(0.5),
    ENCOURAGE_COHERENCY(0.6),
    DISCOURAGE_REPETITION(0.7),
    SOPHISTICATED_VOCABULARY(0.8),
    HARDLY_REPEATS(0.9),
    NEVER_REPEATS(1.0);


    private final double value;

    RepetitionPenalty(double value) {
        this.value = value;
    }

    public double val() {
        return this.value;
    }
}
