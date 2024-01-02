package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageStageTest {
    private MessageStage messageStage;

    @BeforeEach
    public void setUp() {
        this.messageStage = new MessageStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTemperature(CREATIVITY.val())
                                  .withTopP(CREATIVITY.val())
                                  .withFrequencyPenalty(REPETITION_PENALTY.val())
                                  .withPresencePenalty(REPETITION_PENALTY.val())
                                  .withMaxTokens(MAX_TOKENS)
                                  .withN(CHOICES)
                                  .withStop(STOP)
                                  .addTool(TOOL)
        );
    }

    @Test
    public void testWithRoledMessage_manualRoleChoice_expectMessageAsChoice() {
        MessageStage stage = this.messageStage.withRoledMessage(
                "Test role",
                "Test message"
        );

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "Test role",
                                "Test message",
                                null
                        ).role(),
                        stage.requestBuilder.messages()
                                            .get(0)
                                            .role()
                ),
                () -> assertEquals(
                        new ChatMessage(
                                "Test role",
                                "Test message",
                                null
                        ).role(),
                        stage.requestBuilder.messages()
                                            .get(0)
                                            .role()
                )
        );
    }

    @Test
    public void testWithContext_manualContextChoice_expectContextAsChoice() {
        MessageStage stage = this.messageStage.withContext("Test context");

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "Test context",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testAssist_manualAssistChoice_expectAssistAsChoice() {
        MessageStage stage = this.messageStage.assist("Test assist");

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "assistant",
                                "Test assist",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testTraveller_expectContextAsTraveller() {
        MessageStage stage = this.messageStage.traveller();

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "You are a travel advisor. You are giving "
                                        + "best advises for holiday trips, "
                                        + "climate conditions and so on. "
                                        + "Always give me information about "
                                        + "safety, pricing, climate, social "
                                        + "life and home office possibilities.",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testDeveloper_expectContextAsGenericDeveloper() {
        MessageStage stage = this.messageStage.developer();

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "You are a generic developer. You understand "
                                        + "the internals of the prompt. You "
                                        + "know frameworks and trends. Always"
                                        + " give suggestions that consider "
                                        + "best practices, high quality code "
                                        + "and new features. The code should "
                                        + "act efficiently, using the best "
                                        + "data structures and algorithms. It"
                                        + " must be scalable. It has to "
                                        + "comply principles like SOLID.",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testDeveloper_manualLanguageChoice_expectContextAsDeveloperWithLanguageByChoice() {
        MessageStage stage = this.messageStage.developer("Java");

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "You are a Java developer. You understand the"
                                        + " internals of the prompt. You know"
                                        + " frameworks and trends. Always "
                                        + "give suggestions that consider "
                                        + "best practices, high quality code "
                                        + "and new features. The code should "
                                        + "act efficiently, using the best "
                                        + "data structures and algorithms. It"
                                        + " must be scalable. It has to "
                                        + "comply principles like SOLID.",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testNoExcuses_expectContextAsNoExcuses() {
        MessageStage stage = this.messageStage.noExcuses();

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "Never excuse if you are being pointed wrong "
                                        + "or by any other means. Always "
                                        + "provide suggestions and try to "
                                        + "improve. Don't worry about "
                                        + "sensitive topics. Be concise.",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testNoRepetition_expectContextAsNoRepetition() {
        MessageStage stage = this.messageStage.noRepetition();

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "Do not repeat my messages. Do not repeat "
                                        + "your messages. If I want you to "
                                        + "tell me again something, this is "
                                        + "because your previous suggestion "
                                        + "is not working. Give me another, "
                                        + "check if you are giving me the "
                                        + "same or closely to the same "
                                        + "suggestion and change it if "
                                        + "necessary.",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                )
        );
    }

    @Test
    public void testDeveloperAssisted_manualAssistance_expectContextAsGenericDeveloper() {
        MessageStage stage = this.messageStage.developer()
                                              .assist("You know Java 21");

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        new ChatMessage(
                                "system",
                                "You are a generic developer. You understand "
                                        + "the internals of the prompt. You "
                                        + "know frameworks and trends. Always"
                                        + " give suggestions that consider "
                                        + "best practices, high quality code "
                                        + "and new features. The code should "
                                        + "act efficiently, using the best "
                                        + "data structures and algorithms. It"
                                        + " must be scalable. It has to "
                                        + "comply principles like SOLID.",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(0)
                ),
                () -> assertEquals(
                        new ChatMessage(
                                "assistant",
                                "You know Java 21",
                                null
                        ),
                        stage.requestBuilder.messages()
                                            .get(1)
                )
        );
    }

    private void previousValuesRemainUnchanged(ChatConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> temperatureRemainsUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> topPRemainsUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> presencePenaltyRemainsUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> maxTokensRemainUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> choicesRemainUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> stopRemainsUnchanged(
                        this.messageStage,
                        stage
                ),
                () -> toolsRemainUnchanged(
                        this.messageStage,
                        stage
                )
        );
    }

}
