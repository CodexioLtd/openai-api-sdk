package bg.codexio.ai.openai.api.models.tts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TTS1ModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "tts-1",
                new TTS1Model().name()
        );
    }
}
