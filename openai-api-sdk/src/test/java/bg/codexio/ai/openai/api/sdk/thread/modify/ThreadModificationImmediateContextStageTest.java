package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.modify.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ThreadModificationImmediateContextStageTest {

    private ThreadModificationImmediateContextStage threadModificationImmediateContextStage;

    @BeforeEach
    void setUp() {
        this.threadModificationImmediateContextStage =
                new ThreadModificationImmediateContextStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testFinishRaw_expectCorrectResponse() {
        this.mockImmediateExecution();

        assertEquals(
                THREAD_RESPONSE,
                this.threadModificationImmediateContextStage.finishRaw()
        );
    }

    @Test
    void testFinish_expectCorrectResponse() {
        this.mockImmediateExecution();

        assertEquals(
                THREAD_RESPONSE.id(),
                this.threadModificationImmediateContextStage.finish()
        );
    }

    private void mockImmediateExecution() {
        when(this.threadModificationImmediateContextStage.httpExecutor.immediate()
                                                                      .executeWithPathVariable(
                any(),
                any()
        )).thenAnswer(res -> THREAD_RESPONSE);
    }
}
