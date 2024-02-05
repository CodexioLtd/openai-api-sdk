package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageAdvancedConfigurationStageTest {

    private MessageAdvancedConfigurationStage<MessageResponse> messageAdvancedConfigurationStage;

    @BeforeEach
    void setUp() {
        this.messageAdvancedConfigurationStage =
                new MessageAdvancedConfigurationStage<>(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    void testFile_expectCorrectBuilder() {
        var nextStage = this.messageAdvancedConfigurationStage.file();
        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testMeta_expectCorrectBuilder() {
        var nextStage = this.messageAdvancedConfigurationStage.meta();
        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testAssistant_expectCorrectBuilder() {
        var nextStage = this.messageAdvancedConfigurationStage.assistant();
        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testAndResponse_expectCorrectResponse() {
        executeWithPathVariable(this.messageAdvancedConfigurationStage);
        var response = this.messageAdvancedConfigurationStage.andRespond();

        assertEquals(
                MESSAGE_RESPONSE_WITH_TEXT_CONTENT,
                response
        );
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage<MessageResponse> nextStage) {
        assertAll(
                () -> roleRemainsUnchanged(
                        this.messageAdvancedConfigurationStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.messageAdvancedConfigurationStage,
                        nextStage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.messageAdvancedConfigurationStage,
                        nextStage
                ),
                () -> contentRemainsUnchanged(
                        this.messageAdvancedConfigurationStage,
                        nextStage
                )
        );
    }
}
