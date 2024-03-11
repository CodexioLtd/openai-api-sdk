package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.run.RunnableAdvancedConfigurationStage;
import bg.codexio.ai.openai.api.sdk.run.RunnableInitializationStage;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncExecution;
import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private static Stream<Arguments> provideTestVariables() {
        return Stream.of(
                Arguments.of(ASSISTANT_ID),
                Arguments.of(ASSISTANT_RESPONSE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestVariables")
    void testAssistImmediate_withAssistantIdentifiers_expectCorrectBuilder(Object assistantIdentifier) {
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(authenticateRunnable());
            if (assistantIdentifier instanceof String assistantId) {
                this.messageAssistantStage.assistImmediate(assistantId);
            } else if (assistantIdentifier instanceof AssistantResponse assistantResponse) {
                this.messageAssistantStage.assistImmediate(assistantResponse);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestVariables")
    void testAssistAsync_withAssistantIdentifiers_expectCorrectBuilder(Object assistantIdentifier)
            throws JsonProcessingException {
        var callBack =
                prepareCallback(RunnableAdvancedConfigurationStage.class);

        mockAsyncExecution(
                MESSAGE_HTTP_EXECUTOR,
                MESSAGE_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(MESSAGE_RESPONSE)
        );

        if (assistantIdentifier instanceof AssistantResponse assistantResponse) {
            var completableFutureMock =
                    (CompletableFuture<AssistantResponse>) mock(CompletableFuture.class);

            when(completableFutureMock.thenAcceptAsync(any(Consumer.class))).thenAnswer(invocation -> {
                Consumer<AssistantResponse> callback =
                        invocation.getArgument(0);
                callback.accept(assistantResponse);

                return null;
            });

            try (var authUtils = mockStatic(Authenticator.class)) {
                authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                         .thenReturn(authenticateRunnable());
                this.messageAssistantStage.assistAsync(
                        completableFutureMock,
                        callBack.callback()
                );

                assertNotNull(callBack.data());
            }
        } else if (assistantIdentifier instanceof String assistantId) {
            try (var authUtils = mockStatic(Authenticator.class)) {
                authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                         .thenReturn(authenticateRunnable());
                this.messageAssistantStage.assistAsync(
                        assistantId,
                        callBack.callback()
                );

                assertNotNull(callBack.data());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestVariables")
    void testReactive_withAssistantIdentifiers_expectCorrectBuilder(Object assistantIdentifier) {
        when(this.messageAssistantStage.httpExecutor.executeReactiveWithPathVariable(
                any(),
                any()
        )).thenAnswer(answer -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(MESSAGE_RESPONSE)
        ));

        if (assistantIdentifier instanceof AssistantResponse assistantResponse) {
            try (var authUtils = mockStatic(Authenticator.class)) {
                authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                         .thenReturn(authenticateRunnable());
                var result =
                        this.messageAssistantStage.assistReactive(Mono.just(assistantResponse))
                                                       .block();

                assertNotNull(result);
            }
        } else if (assistantIdentifier instanceof String assistantId) {
            try (var authUtils = mockStatic(Authenticator.class)) {
                authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                         .thenReturn(authenticateRunnable());
                var result =
                        this.messageAssistantStage.assistReactive(assistantId)
                                                       .block();

                assertNotNull(result);
            }
        }
    }

    private HttpBuilder<RunnableInitializationStage> authenticateRunnable() {
        return Runnables.authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );
    }
}