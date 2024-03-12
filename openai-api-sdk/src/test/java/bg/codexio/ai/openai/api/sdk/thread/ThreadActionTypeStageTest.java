package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadActionTypeStageTest {

    private ThreadActionTypeStage threadActionTypeStage;

    private static Stream<Arguments> provideThreadTestVariables() {
        return Stream.of(
                Arguments.of(THREAD_ID),
                Arguments.of(THREAD_RESPONSE)
        );
    }

    @BeforeEach
    void setUp() {
        this.threadActionTypeStage = new ThreadActionTypeStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                MODIFY_THREAD_HTTP_EXECUTOR
        );
    }

    @Test
    void testCreating_expectCorrectBuilder() {
        assertNotNull(this.threadActionTypeStage.creating());
    }

    @ParameterizedTest
    @MethodSource("provideThreadTestVariables")
    void testModify_withThreadVariables_expectCorrectBuilder(Object threadTestVariables) {
        if (threadTestVariables instanceof String threadId) {
            assertNotNull(this.threadActionTypeStage.modify(threadId));
        } else if (threadTestVariables instanceof ThreadResponse threadResponse) {
            assertNotNull(this.threadActionTypeStage.modify(threadResponse));
        }
    }

}
