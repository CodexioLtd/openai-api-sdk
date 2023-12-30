package bg.codexio.ai.openai.api.models.v35;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPT35Turbo1106ModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "gpt-3.5-turbo-1106",
                new GPT35Turbo1106Model().name()
        );
    }
}
