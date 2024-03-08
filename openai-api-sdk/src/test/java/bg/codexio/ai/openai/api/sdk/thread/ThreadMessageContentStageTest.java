package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.thread.create.ThreadConfigurationStage;
import bg.codexio.ai.openai.api.sdk.thread.create.ThreadMessageContentStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.DEFAULT_ROLE;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ThreadMessageContentStageTest {

    private ThreadMessageContentStage threadMessageContentStage;

    @BeforeEach
    void setUp() {
        this.threadMessageContentStage = new ThreadMessageContentStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_CREATION_REQUEST_BUILDER
        );
    }

    @Test
    void testStartWith_withContentVarArgs_expectCorrectBuilder() {
        var nextStage =
                this.threadMessageContentStage.startWith(THREAD_MESSAGE_CONTENT_ARGS);

        assertAll(
                () -> metadataRemainsUnchanged(
                        this.threadMessageContentStage,
                        nextStage
                ),
                () -> assertEquals(
                        THREAD_MESSAGE_CONTENT_ARGS[0],
                        this.getMessageRequest(nextStage)
                            .content()
                ),
                () -> assertEquals(
                        DEFAULT_ROLE,
                        this.getMessageRequest(nextStage)
                            .role()
                )
        );
    }

    @Test
    void testStartWith_withSingleContent_expectCorrectBuilder() {
        var nextStage =
                this.threadMessageContentStage.startWith(THREAD_MESSAGE_CONTENT);
        assertAll(
                () -> metadataRemainsUnchanged(
                        this.threadMessageContentStage,
                        nextStage
                ),
                () -> assertNotNull(nextStage.httpExecutor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    private MessageRequest getMessageRequest(ThreadConfigurationStage<ThreadCreationRequest> nextStage) {
        return nextStage.requestBuilder.messages()
                                       .get(0);
    }
}