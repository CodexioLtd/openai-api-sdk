package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.modify.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadModificationRuntimeSelectionStageTest {

    private ThreadModificationRuntimeSelectionStage threadModificationRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.threadModificationRuntimeSelectionStage =
                new ThreadModificationRuntimeSelectionStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        assertNotNull(this.threadModificationRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        assertNotNull(this.threadModificationRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        assertNotNull(this.threadModificationRuntimeSelectionStage.reactive());
    }
}