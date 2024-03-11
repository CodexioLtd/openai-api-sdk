package bg.codexio.ai.openai.api.sdk.thread;

import org.junit.jupiter.api.BeforeEach;

import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;

public class ThreadActionTypeStageTest {

    private ThreadActionTypeStage threadActionTypeStage;

    @BeforeEach
    void setUp() {
        this.threadActionTypeStage = new ThreadActionTypeStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                MODIFY_THREAD_HTTP_EXECUTOR
        );
    }

    //    @Test
    //    void testCreating_expectCorrectBuilder() {
    //        var nextStage = this.threadActionTypeStage.creating();
    //
    //        assertAll(
    //                () -> assertEquals(
    //                        nextStage.httpExecutor,
    //                        CREATE_THREAD_HTTP_EXECUTOR
    //                ),
    //                () -> createThreadRequestIsPopulated(nextStage)
    //        );
    //    }
    //
    //    @Test
    //    void testModify_withThreadId_expectCorrectBuilder() {
    //        var nextStage = this.threadActionTypeStage.modify(THREAD_ID);
    //
    //        assertAll(
    //                () -> assertEquals(
    //                        nextStage.httpExecutor,
    //                        MODIFY_THREAD_HTTP_EXECUTOR
    //                ),
    //                () -> modifyThreadRequestIsPopulated(nextStage)
    //        );
    //    }
    //
    //    @Test
    //    void testModify_withThreadResponse_expectCorrectBuilder() {
    //        var nextStage = this.threadActionTypeStage.modify
    //        (THREAD_RESPONSE);
    //
    //        assertAll(
    //                () -> assertEquals(
    //                        nextStage.httpExecutor,
    //                        MODIFY_THREAD_HTTP_EXECUTOR
    //                ),
    //                () -> modifyThreadRequestIsPopulated(nextStage)
    //        );
    //    }
}
