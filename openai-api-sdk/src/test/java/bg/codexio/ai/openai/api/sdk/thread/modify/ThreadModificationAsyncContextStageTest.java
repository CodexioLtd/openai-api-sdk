package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.modify.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadModificationAsyncContextStageTest {

    private ThreadModificationAsyncContextStage threadModificationAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.threadModificationAsyncContextStage =
                new ThreadModificationAsyncContextStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testFinishRaw_expectCorrectBuilder() {
        assertNotNull(this.threadModificationAsyncContextStage.finishRaw());
    }
}
