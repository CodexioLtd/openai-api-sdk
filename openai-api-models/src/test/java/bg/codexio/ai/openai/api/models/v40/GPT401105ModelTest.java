package bg.codexio.ai.openai.api.models.v40;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPT401105ModelTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "gpt-4-1106-preview",
                new GPT401106Model().name()
        );
    }
}
