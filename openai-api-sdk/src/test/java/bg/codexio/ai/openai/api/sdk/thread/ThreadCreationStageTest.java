package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.thread.create.ThreadCreationStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ThreadCreationStageTest {

    private ThreadCreationStage<ThreadCreationRequest> threadCreationStage;

    @BeforeEach
    void setUp() {
        this.threadCreationStage = new ThreadCreationStage<>(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_CREATION_REQUEST_BUILDER
        );
    }

    @Test
    void testEmpty_expectCorrectResponse() {
        execute(this.threadCreationStage);
        var response = this.threadCreationStage.empty();

        assertEquals(
                THREAD_RESPONSE,
                response
        );
    }

    @Test
    void testDeepConfigure_expectCorrectBuilder() {
        var nextStage = this.threadCreationStage.deepConfigure();

        assertAll(
                () -> assertNotNull(nextStage.httpExecutor),
                () -> createThreadRequestIsPopulated(nextStage)
        );
    }
}
