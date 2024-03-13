package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadAsyncContextStageTest {

    private ThreadAsyncContextStage threadAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.threadAsyncContextStage = new ThreadAsyncContextStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );
    }

    @Test
    public void testFinishRaw_expectCorrectBuilder() {
        assertNotNull(this.threadAsyncContextStage.finishRaw());
    }
}
