package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadCreationStageTest {

    private ThreadCreationStage threadCreationStage;

    @BeforeEach
    void setUp() {
        this.threadCreationStage = new ThreadCreationStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );
    }

    @Test
    void testEmpty_expectCorrectResponse() {
        assertNotNull(this.threadCreationStage.empty());
    }

    @Test
    void testDeepConfigure_expectCorrectBuilder() {
        assertNotNull(this.threadCreationStage.deepConfigure());
    }
}