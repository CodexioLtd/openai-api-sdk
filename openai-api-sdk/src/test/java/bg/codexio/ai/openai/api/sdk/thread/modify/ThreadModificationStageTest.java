package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.modify.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.*;

public class ThreadModificationStageTest {

    private ThreadModificationStage threadModificationStage;

    @BeforeEach
    void setUp() {
        this.threadModificationStage = new ThreadModificationStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testWithMeta_expectCorrectBuilder() {
        var nextStage =
                this.threadModificationStage.withMeta(METADATA_VAR_ARGS);

        assertAll(
                () -> assertEquals(
                        METADATA_MAP,
                        nextStage.requestBuilder.metadata()
                ),
                () -> assertNotNull(nextStage.httpExecutor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    void testAndResponse_expectCorrectBuilder() {
        var nextStage = this.threadModificationStage.andRespond();

        assertAll(
                () -> assertNotNull(nextStage.httpExecutor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    void testChat_expectCorrectBuilder() {
        assertNotNull(this.threadModificationStage.chat());
    }
}
