package bg.codexio.ai.openai.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTypeAbstractTest {

    @Test
    public void testConstructor_expectCorrectModelName() {
        var model = new ModelTypeAbstract("test-model") {};

        assertAll(
                () -> assertEquals(
                        "test-model",
                        model.name()
                ),
                () -> assertEquals(
                        "test-model",
                        model.toString()
                )
        );
    }
}
