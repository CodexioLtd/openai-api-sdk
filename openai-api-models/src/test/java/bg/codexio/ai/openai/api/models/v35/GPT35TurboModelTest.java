package bg.codexio.ai.openai.api.models.v35;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPT35TurboModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "gpt-3.5-turbo",
                new GPT35TurboModel().name()
        );
    }
}
