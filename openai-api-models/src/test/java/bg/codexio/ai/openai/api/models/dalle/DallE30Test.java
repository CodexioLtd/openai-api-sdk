package bg.codexio.ai.openai.api.models.dalle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DallE30Test {

    @Test
    public void testConstructor_expectCorrectModelName() {
        assertEquals(
                "dall-e-3",
                new DallE30().name()
        );
    }
}
