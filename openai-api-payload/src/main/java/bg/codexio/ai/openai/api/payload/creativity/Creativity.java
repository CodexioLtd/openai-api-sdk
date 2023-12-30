package bg.codexio.ai.openai.api.payload.creativity;

public enum Creativity {
    TRULY_DETERMINISTIC(0.0),
    MOSTLY_DETERMINISTIC(0.1),
    INTRODUCE_SOME_RANDOMNESS(0.2),
    FIRST_JUMP_TO_CREATIVITY(0.3),
    INTRODUCE_UNIQUENESS(0.4),
    BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY(0.5),
    BE_LESS_OBVIOUS(0.6),
    INVENTIVE_AND_UNEXPECTED(0.7),
    I_AM_WRITING_SONGS(0.8),
    DONT_TRUST_ME(0.9),
    I_HAVE_NO_IDEA_WHAT_I_AM_TALKING(1.0);


    private final double value;

    Creativity(double value) {
        this.value = value;
    }

    public double val() {
        return this.value;
    }
}
