package bg.codexio.ai.openai.api.models.whisper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Whisper10Test {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "whisper-1",
                new Whisper10().name()
        );
    }
}
