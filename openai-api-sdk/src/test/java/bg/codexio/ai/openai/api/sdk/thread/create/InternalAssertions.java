package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InternalAssertions {
    static final CreateThreadHttpExecutor CREATE_THREAD_HTTP_EXECUTOR =
            mock(CreateThreadHttpExecutor.class);

    static final String[] THREAD_MESSAGE_CONTENT_ARGS = new String[]{
            "test_message_content_1", "test_message_content_2"
    };
    static final String THREAD_MESSAGE_CONTENT = "test_message_content_3";

    static void messagesRemainsUnchanged(
            ThreadConfigurationStage previousStage,
            ThreadConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.messages(),
                nextStage.requestBuilder.messages()
        );
    }

    static void metadataRemainsUnchanged(
            ThreadConfigurationStage previousStage,
            ThreadConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.metadata(),
                nextStage.requestBuilder.metadata()
        );
    }

}
