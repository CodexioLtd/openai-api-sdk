package bg.codexio.ai.openai.api.models.v35;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPT35Turbo16kModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "gpt-3.5-turbo-16k",
                new GPT35Turbo16kModel().name()
        );
    }
}
