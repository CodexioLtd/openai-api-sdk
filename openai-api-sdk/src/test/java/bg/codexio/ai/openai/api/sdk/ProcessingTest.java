package bg.codexio.ai.openai.api.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessingTest {

    @Test
    public void testRealTime_shouldBeTrue() {
        assertTrue(Processing.REAL_TIME.val());
    }

    @Test
    public void testBatch_shouldBeFalse() {
        assertFalse(Processing.BATCH.val());
    }

    @Test
    public void testDefault_shouldBeFalse() {
        assertFalse(Processing.DEFAULT.val());
    }
}
