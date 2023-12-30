package bg.codexio.ai.openai.api.models.v40;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPT40ModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "gpt-4",
                new GPT40Model().name()
        );
    }
}
