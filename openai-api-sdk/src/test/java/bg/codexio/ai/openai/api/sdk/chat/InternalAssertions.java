package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.v40.GPT4032kModel;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.functions.GetNearbyPlaces;
import bg.codexio.ai.openai.api.payload.chat.request.FunctionTool;
import bg.codexio.ai.openai.api.payload.chat.response.*;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.creativity.RepetitionPenalty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InternalAssertions {
    static final ModelTypeAbstract MODEL_TYPE = new GPT4032kModel();
    static final Creativity CREATIVITY = Creativity.FIRST_JUMP_TO_CREATIVITY;
    static final RepetitionPenalty REPETITION_PENALTY =
            RepetitionPenalty.SLIGHT_REPETITION_DISCOURAGE;
    static final int MAX_TOKENS = 100;
    static final int CHOICES = 1;
    static final String[] STOP = new String[]{"Stop"};

    static final String FIRST_QUESTION = "First Question";
    static final String SECOND_QUESTION = "Second Question";
    static final FunctionTool TOOL = GetNearbyPlaces.FUNCTION;
    static final ChatHttpExecutor CHAT_EXECUTOR = mock(ChatHttpExecutor.class);
    static final ChatUsageResponse CHAT_USAGE_RESPONSE = new ChatUsageResponse(
            1,
            1,
            1
    );
    static final ChatMessage CHAT_MESSAGE_1 = new ChatMessage(
            "test-role",
            "test-response-1",
            null
    );
    static final ChatMessage CHAT_MESSAGE_2 = new ChatMessage(
            "test-role",
            "test-response-2",
            null
    );
    static final FunctionResponse FUNCTION_RESPONSE = new FunctionResponse(
            "test-function",
            "test-arguments"
    );
    static final ToolCallResponse TOOL_CALL_RESPONSE = new ToolCallResponse(
            1,
            "test-id",
            "test-type",
            InternalAssertions.FUNCTION_RESPONSE
    );
    static final ChatMessage CHAT_MESSAGE_WITH_TOOL_CALLS = new ChatMessage(
            "test-role",
            null,
            List.of(InternalAssertions.TOOL_CALL_RESPONSE)
    );
    static final ChatChoiceResponse CHAT_CHOICE_RESPONSE_1 =
            new ChatChoiceResponse(
                    InternalAssertions.CHAT_MESSAGE_1,
                    InternalAssertions.CHAT_MESSAGE_1,
                    "test-finish-reason",
                    1
            );
    static final ChatChoiceResponse CHAT_CHOICE_RESPONSE_2 =
            new ChatChoiceResponse(
                    InternalAssertions.CHAT_MESSAGE_2,
                    InternalAssertions.CHAT_MESSAGE_2,
                    "test-finish-reason",
                    1
            );
    static final ChatChoiceResponse CHAT_CHOICE_RESPONSE_3 =
            new ChatChoiceResponse(
                    InternalAssertions.CHAT_MESSAGE_WITH_TOOL_CALLS,
                    InternalAssertions.CHAT_MESSAGE_WITH_TOOL_CALLS,
                    "test-finish-reason",
                    1
            );
    static final ChatMessageResponse CHAT_MESSAGE_RESPONSE =
            new ChatMessageResponse(
                    "test-id",
                    "test-object",
                    0,
                    "test-model",
                    CHAT_USAGE_RESPONSE,
                    List.of(
                            CHAT_CHOICE_RESPONSE_1,
                            CHAT_CHOICE_RESPONSE_2
                    )
            );
    static final ChatMessageResponse CHAT_MESSAGE_RESPONSE_2 =
            new ChatMessageResponse(
                    "test-id",
                    "test-object",
                    0,
                    "test-model",
                    CHAT_USAGE_RESPONSE,
                    List.of(CHAT_CHOICE_RESPONSE_3)
            );
    static final ChatMessage CHAT_MESSAGE = new ChatMessage(
            "Test role",
            "Test message",
            null
    );

    static void modelRemainsUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.model(),
                nextStage.requestBuilder.model()
        );
    }

    static void temperatureRemainsUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.temperature(),
                nextStage.requestBuilder.temperature()
        );
    }

    static void topPRemainsUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.topP(),
                nextStage.requestBuilder.topP()
        );
    }

    static void frequencyPenaltyRemainsUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.frequencyPenalty(),
                nextStage.requestBuilder.frequencyPenalty()
        );
    }

    static void presencePenaltyRemainsUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.presencePenalty(),
                nextStage.requestBuilder.presencePenalty()
        );
    }

    static void maxTokensRemainUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.maxTokens(),
                nextStage.requestBuilder.maxTokens()
        );
    }

    static void choicesRemainUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.n(),
                nextStage.requestBuilder.n()
        );
    }

    static void stopRemainsUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.stop(),
                nextStage.requestBuilder.stop()
        );
    }

    static void toolsRemainUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.tools(),
                nextStage.requestBuilder.tools()
        );
    }

    static void messagesRemainUnchanged(
            ChatConfigurationStage previousStage,
            ChatConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.messages(),
                nextStage.requestBuilder.messages()
        );
    }

}
