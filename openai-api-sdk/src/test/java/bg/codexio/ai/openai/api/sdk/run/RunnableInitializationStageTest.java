package bg.codexio.ai.openai.api.sdk.run;

import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.execute;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RunnableInitializationStageTest {

    private RunnableInitializationStage runnableInitializationStage;

    //    @BeforeEach
    //    void setUp() {
    //        this.runnableInitializationStage = new
    //        RunnableInitializationStage(
    //                RUNNABLE_HTTP_EXECUTOR,
    //                RunnableRequest.builder(),
    //                THREAD_ID
    //        );
    //    }
    //
    //    @Test
    //    void testExecuteRaw_withAssistantResponse_expectCorrectResponse() {
    //        execute(this.runnableInitializationStage);
    //        var response =
    //                this.runnableInitializationStage.executeRaw
    //                (ASSISTANT_RESPONSE);
    //
    //        assertEquals(
    //                RUNNABLE_RESPONSE,
    //                response
    //        );
    //    }
    //
    //    @Test
    //    void testExecuteRaw_withAssistantId_expectCorrectResponse() {
    //        execute(this.runnableInitializationStage);
    //        var response =
    //                this.runnableInitializationStage.executeRaw(ASSISTANT_ID);
    //
    //        assertEquals(
    //                RUNNABLE_RESPONSE,
    //                response
    //        );
    //    }
    //
    //    @Test
    //    void testExecute_withAssistantResponse_expectCorrectResponse() {
    //        execute(this.runnableInitializationStage);
    //        var response =
    //                this.runnableInitializationStage.execute
    //                (ASSISTANT_RESPONSE);
    //
    //        assertEquals(
    //                RUNNABLE_ID,
    //                response
    //        );
    //    }
    //
    //    @Test
    //    void testExecute_withAssistantId_expectCorrectResponse() {
    //        execute(this.runnableInitializationStage);
    //        var response = this.runnableInitializationStage.execute
    //        (ASSISTANT_ID);
    //
    //        assertEquals(
    //                RUNNABLE_ID,
    //                response
    //        );
    //    }
    //
    //    @Test
    //    void testRun_withAssistantResponse_expectCorrectBuilder() {
    //        execute(this.runnableInitializationStage);
    //        var nextStage =
    //                this.runnableInitializationStage.run(ASSISTANT_RESPONSE);
    //
    //        assertNotNull(nextStage.requestBuilder.assistantId());
    //    }
    //
    //    @Test
    //    void testRun_withAssistantId_expectCorrectBuilder() {
    //        execute(this.runnableInitializationStage);
    //        var nextStage = this.runnableInitializationStage.run
    //        (ASSISTANT_ID);
    //
    //        assertNotNull(nextStage.requestBuilder.assistantId());
    //    }
    //
    //    @Test
    //    void testMessaging_expectCorrectBuilder() {
    //        this.runnableInitializationStage.messaging();
    //
    //        assertAll(
    //                () -> assertNotNull(this.runnableInitializationStage
    //                .threadId),
    //                () -> assertNotNull(this.runnableInitializationStage
    //                .httpExecutor),
    //                () -> assertNotNull(this.runnableInitializationStage
    //                .requestBuilder.assistantId())
    //        );
    //    }

    @Test
    void testDeepConfigure_withAssistantResponse_expectCorrectBuilder() {
        execute(this.runnableInitializationStage);
        var nextStage =
                this.runnableInitializationStage.deepConfigure(ASSISTANT_RESPONSE);

        assertNotNull(nextStage.requestBuilder.assistantId());
    }

    @Test
    void testDeepConfigure_withAssistantId_expectCorrectBuilder() {
        execute(this.runnableInitializationStage);
        var nextStage =
                this.runnableInitializationStage.deepConfigure(ASSISTANT_ID);

        assertNotNull(nextStage.requestBuilder.assistantId());
    }
}