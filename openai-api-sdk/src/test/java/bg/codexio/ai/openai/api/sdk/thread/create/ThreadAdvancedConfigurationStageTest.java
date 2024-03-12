package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadAdvancedConfigurationStageTest {

    private ThreadAdvancedConfigurationStage threadAdvancedConfigurationStage;

    @BeforeEach
    void setUp() {
        this.threadAdvancedConfigurationStage =
                new ThreadAdvancedConfigurationStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );
    }

    @Test
    void testMeta_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.threadAdvancedConfigurationStage.meta());
    }

    @Test
    void testMessage_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.threadAdvancedConfigurationStage.message());
    }

    @Test
    void testFile_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.threadAdvancedConfigurationStage.file());
    }

    @Test
    void testAndRespond_expectCorrectResponse() {
        this.previousValuesRemainsUnchanged(this.threadAdvancedConfigurationStage.andRespond());
    }

    @Test
    void testChat_expectCorrectBuilder() {
        assertNotNull(this.threadAdvancedConfigurationStage.chat());
    }

    private void previousValuesRemainsUnchanged(ThreadConfigurationStage nextStage) {
        assertAll(
                () -> messagesRemainsUnchanged(
                        this.threadAdvancedConfigurationStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.threadAdvancedConfigurationStage,
                        nextStage
                )
        );
    }
}