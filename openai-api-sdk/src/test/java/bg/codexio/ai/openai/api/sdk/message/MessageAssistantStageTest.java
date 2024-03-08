package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.MESSAGE_CONTENT;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.MESSAGE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class MessageAssistantStageTest {

    private MessageAssistantStage messageAssistantStage;

    @BeforeEach
    void setUp() {
        this.messageAssistantStage = new MessageAssistantStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    void testAssist_withAssistantId_expectCorrectBuilder() {
        var auth = Runnables.authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);
            this.messageAssistantStage.assistImmediate(ASSISTANT_ID);
        }
    }

    @Test
    void testAssist_withAssistantResponse_expectCorrectBuilder() {
        var auth = Runnables.authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);
            this.messageAssistantStage.assistImmediate(ASSISTANT_RESPONSE);
        }
    }
}