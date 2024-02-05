package bg.codexio.ai.openai.api.sdk;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class ThreadOperationUtilsTest {

    @Test
    void testSleep_expectCorrectTimeout() {
        long expectedMillis = 1000;
        long exceedingAmount = expectedMillis + 300;

        assertTimeout(
                Duration.ofMillis(exceedingAmount),
                () -> ThreadOperationUtils.sleep(expectedMillis)
        );
    }

}
