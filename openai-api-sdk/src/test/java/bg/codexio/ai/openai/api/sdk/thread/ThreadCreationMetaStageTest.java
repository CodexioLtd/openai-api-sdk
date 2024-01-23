package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.METADATA_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadCreationMetaStageTest {

    private ThreadMetaStage<ThreadCreationRequest> threadMetaStage;

    @BeforeEach
    void setUp() {
        this.threadMetaStage = new ThreadMetaStage<>(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_CREATION_REQUEST_BUILDER
        );
    }

    @Test
    void testAwareOf_expectCorrectBuilder() {
        var nextStage = this.threadMetaStage.awareOf(METADATA_VAR_ARGS);

        assertAll(
                () -> messagesRemainsUnchanged(
                        this.threadMetaStage,
                        nextStage
                ),
                () -> assertEquals(
                        METADATA_MAP,
                        nextStage.requestBuilder.metadata()
                )
        );

    }
}
