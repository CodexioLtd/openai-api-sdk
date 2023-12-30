package bg.codexio.ai.openai.api.models.v40;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPT40302kModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "gpt-4-32k",
                new GPT4032kModel().name()
        );
    }
}
