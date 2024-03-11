package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MessageAdvancedConfigurationStageTest {

    private MessageAdvancedConfigurationStage messageAdvancedConfigurationStage;

    @BeforeEach
    void setUp() {
        this.messageAdvancedConfigurationStage =
                new MessageAdvancedConfigurationStage(
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
        var nextStage = this.messageAdvancedConfigurationStage.andRespond();
        this.previousValuesRemainsUnchanged(nextStage);
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage nextStage) {
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