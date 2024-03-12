package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.messagesRemainsUnchanged;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadMetaStageTest {

    private ThreadMetaStage threadMetaStage;

    @BeforeEach
    void setUp() {
        this.threadMetaStage = new ThreadMetaStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
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
