package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_MODIFICATION_REQUEST_THREAD_REQUEST_BUILDER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadModificationMetaStageTest {

    private ThreadMetaStage<ThreadModificationRequest> threadMetaStage;

    @BeforeEach
    void setUp() {
        this.threadMetaStage = new ThreadMetaStage<>(
                MODIFY_THREAD_HTTP_EXECUTOR,
                THREAD_MODIFICATION_REQUEST_THREAD_REQUEST_BUILDER
        );
    }

    @Test
    void testAwareOf_expectCorrectBuilder() {
        var nextStage = this.threadMetaStage.awareOf(METADATA_VAR_ARGS);

        assertEquals(
                METADATA_MAP,
                nextStage.requestBuilder.metadata()
        );
    }

}
