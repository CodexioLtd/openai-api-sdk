package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.payload.run.response.action.Function;
import bg.codexio.ai.openai.api.payload.run.response.action.RequiredAction;
import bg.codexio.ai.openai.api.payload.run.response.action.SubmitToolOutput;
import bg.codexio.ai.openai.api.payload.run.response.action.ToolCall;
import bg.codexio.ai.openai.api.payload.run.response.error.LastError;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.message.MessageActionTypeStage;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import org.mockito.MockedStatic;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalAssertions {

    public static final String RUNNABLE_ID = "runnable_test_id";
    static final RunnableHttpExecutor RUNNABLE_HTTP_EXECUTOR =
            mock(RunnableHttpExecutor.class);
    static final String RUNNABLE_COMPLETED_STATUS = "completed";
    static final String IN_PROGRESS_STATUS = "in_progress";
    static final ModelType RUNNABLE_MODEL_TYPE = new GPT40Model();
    static final String RUNNABLE_ADDITIONAL_INSTRUCTIONS = "test_instructions";
    static final RunnableResponse RUNNABLE_RESPONSE = new RunnableResponse(
            RUNNABLE_ID,
            "test_object",
            0,
            THREAD_ID,
            ASSISTANT_ID,
            IN_PROGRESS_STATUS,
            new RequiredAction(
                    "submit_tool_outputs",
                    new SubmitToolOutput(List.of(new ToolCall(
                            "test_tool_call_id",
                            "test_tool_call_type",
                            new Function(
                                    "test_function_name",
                                    "test_function_arguments"
                            )
                    )))
            ),
            new LastError(
                    "error_test_code",
                    "error_test_message"
            ),
            0,
            0,
            0,
            0,
            0,
            RUNNABLE_MODEL_TYPE.name(),
            RUNNABLE_ADDITIONAL_INSTRUCTIONS,
            List.of(new CodeInterpreter()),
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );
    static final RunnableResponse RUNNABLE_RESPONSE_WITH_EMPTY_TOOLS =
            new RunnableResponse(
            RUNNABLE_ID,
            "test_object",
            0,
            THREAD_ID,
            ASSISTANT_ID,
            "test_status",
            new RequiredAction(
                    "submit_tool_outputs",
                    new SubmitToolOutput(List.of(new ToolCall(
                            "test_tool_call_id",
                            "test_tool_call_type",
                            new Function(
                                    "test_function_name",
                                    "test_function_arguments"
                            )
                    )))
            ),
            new LastError(
                    "error_test_code",
                    "error_test_message"
            ),
            0,
            0,
            0,
            0,
            0,
            RUNNABLE_MODEL_TYPE.name(),
            RUNNABLE_ADDITIONAL_INSTRUCTIONS,
            new ArrayList<>(),
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );
    static final RunnableResponse RUNNABLE_RESPONSE_WITH_COMPLETED_STATUS =
            new RunnableResponse(
            RUNNABLE_ID,
            "test_object",
            0,
            THREAD_ID,
            ASSISTANT_ID,
            RUNNABLE_COMPLETED_STATUS,
            new RequiredAction(
                    "submit_tool_outputs",
                    new SubmitToolOutput(List.of(new ToolCall(
                            "test_tool_call_id",
                            "test_tool_call_type",
                            new Function(
                                    "test_function_name",
                                    "test_function_arguments"
                            )
                    )))
            ),
            new LastError(
                    "error_test_code",
                    "error_test_message"
            ),
            0,
            0,
            0,
            0,
            0,
            RUNNABLE_MODEL_TYPE.name(),
            "test_instructions",
            List.of(new CodeInterpreter()),
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );

    static void mockImmediateExecutionWithPathVariablesWithCompletedStatus(RunnableConfigurationStage runnableConfigurationStage) {
        when(runnableConfigurationStage.httpExecutor.immediate()
                                                    .retrieve(
                any(),
                any()
        )).thenAnswer(res -> RUNNABLE_RESPONSE_WITH_COMPLETED_STATUS);

    }

    static void assistantIdRemainsUnchanged(
            RunnableConfigurationStage previousStage,
            RunnableConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.assistantId(),
                nextStage.requestBuilder.assistantId()
        );
    }

    static void modelRemainsUnchanged(
            RunnableConfigurationStage previousStage,
            RunnableConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.model(),
                nextStage.requestBuilder.model()
        );
    }

    static void additionalInstructionsRemainsUnchanged(
            RunnableConfigurationStage previousStage,
            RunnableConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.additionalInstructions(),
                nextStage.requestBuilder.additionalInstructions()
        );
    }

    static void metadataRemainsUnchanged(
            RunnableConfigurationStage previousStage,
            RunnableConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.metadata(),
                nextStage.requestBuilder.metadata()
        );
    }

    static void mockImmediateExecution(RunnableConfigurationStage runnableConfigurationStage) {
        when(runnableConfigurationStage.httpExecutor.immediate()
                                                    .executeWithPathVariable(
                any(),
                any()
        )).thenAnswer(res -> RUNNABLE_RESPONSE);
    }

    static void mockReactiveExecution(RunnableConfigurationStage runnableConfigurationStage) {
        when(runnableConfigurationStage.httpExecutor.reactive()
                                                    .executeWithPathVariable(
                any(),
                any()
                                                    )).thenAnswer(res -> new ReactiveHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(RUNNABLE_RESPONSE)
        ));
    }

    static RetrieveListMessagesHttpExecutor mockMessageProcessing(MockedStatic<Messages> mockedMessage) {
        var httpBuilderMock = mock(HttpBuilder.class);
        var retrieveMessageHttpExecutorMock =
                mock(RetrieveListMessagesHttpExecutor.class);
        mockedMessage.when(() -> Messages.defaults((String) any()))
                     .thenReturn(httpBuilderMock);
        mockedMessage.when(httpBuilderMock::and)
                     .thenReturn(new MessageActionTypeStage(
                             null,
                             retrieveMessageHttpExecutorMock,
                             THREAD_ID
                     ));

        return retrieveMessageHttpExecutorMock;
    }
}