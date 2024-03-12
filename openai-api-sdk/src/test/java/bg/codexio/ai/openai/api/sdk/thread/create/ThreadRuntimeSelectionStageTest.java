package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadRuntimeSelectionStageTest {

    private ThreadRuntimeSelectionStage threadRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.threadRuntimeSelectionStage = new ThreadRuntimeSelectionStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        assertNotNull(this.threadRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        assertNotNull(this.threadRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        assertNotNull(this.threadRuntimeSelectionStage.reactive());
    }
}
