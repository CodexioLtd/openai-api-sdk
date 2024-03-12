package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RunnableInitializationStageTest {

    private RunnableInitializationStage runnableInitializationStage;

    private static Stream<Arguments> provideAssistantTestVariables() {
        return Stream.of(
                Arguments.of(ASSISTANT_ID),
                Arguments.of(ASSISTANT_RESPONSE)
        );
    }

    @BeforeEach
    void setUp() {
        this.runnableInitializationStage = new RunnableInitializationStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder(),
                THREAD_ID
        );
    }

    @ParameterizedTest
    @MethodSource("provideAssistantTestVariables")
    public void testInitialize_withAssistantTestVariables_expectCorrectResponse(Object assistantVariables) {
        if (assistantVariables instanceof String assistantId) {
            assertEquals(
                    assistantId,
                    this.runnableInitializationStage.initialize(assistantId).requestBuilder.assistantId()
            );
        } else if (assistantVariables instanceof AssistantResponse assistantResponse) {
            assertEquals(
                    assistantResponse.id(),
                    this.runnableInitializationStage.initialize(assistantResponse).requestBuilder.assistantId()
            );
        }
    }

    @ParameterizedTest
    @MethodSource("provideAssistantTestVariables")
    public void testDeepConfigure_withAssistantTestVariables_expectCorrectResponse(Object assistantVariables) {
        if (assistantVariables instanceof String assistantId) {
            assertEquals(
                    assistantId,
                    this.runnableInitializationStage.deepConfigure(assistantId).requestBuilder.assistantId()
            );
        } else if (assistantVariables instanceof AssistantResponse assistantResponse) {
            assertEquals(
                    assistantResponse.id(),
                    this.runnableInitializationStage.deepConfigure(assistantResponse).requestBuilder.assistantId()
            );
        }
    }

    @Test
    public void testAndRespond_expectCorrectBuilder() {
        assertNotNull(this.runnableInitializationStage.andRespond());
    }
}