package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.response.ChatChoiceResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatUsageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ReactivePromptStageTest {

    private ReactivePromptStage promptStage;

    private static ChatMessageResponse mockResponse() {
        return new ChatMessageResponse(
                "",
                "",
                1L,
                "",
                new ChatUsageResponse(
                        1,
                        2,
                        3
                ),
                List.of(new ChatChoiceResponse(
                        new ChatMessage(
                                "user",
                                "test-response",
                                List.of()
                        ),
                        null,
                        null,
                        0
                ))
        );
    }

    @BeforeEach
    public void setUp() {
        this.promptStage = new ReactivePromptStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
                             .withTokens(TEST_TOKENS)
                             .withMessages(new MessageContentHolder(List.of(new ImageUrlMessageRequest(new ImageUrlRequest(
                                     TEST_IMAGE,
                                     TEST_ANALYZE
                             )))))
        );
    }

    @Test
    public void testDescribe_withoutPrompt_expectDefaultPrompt() {
        this.testExecution(
                "What’s in this image?",
                () -> this.promptStage.describe()
        );
    }

    @Test
    public void testDescribe_expectManualPrompt() {
        this.testExecution(
                "What is this?",
                () -> this.promptStage.describe("What is this?")
        );
    }

    private void testExecution(
            String prompt,
            Supplier<OpenAIHttpExecutor.ReactiveExecution<?>> executionSupplier
    ) {
        var expectedRequest = this.expectWith(prompt);

        when(TEST_EXECUTOR.executeReactive(eq(expectedRequest))).thenReturn(new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.fromStream(Stream.of(
                        "first",
                        "second"
                )),
                Mono.just(mockResponse())
        ));

        var execution = executionSupplier.get();

        var lines = String.join(
                ",",
                execution.getLines()
                         .collectList()
                         .block()
        );
        var response = execution.getResponse()
                                .block();
        assertAll(
                () -> assertEquals(
                        "first,second",
                        lines
                ),
                () -> assertEquals(
                        mockResponse(),
                        response
                )
        );
    }

    private VisionRequest expectWith(String message) {
        return this.promptStage.requestContext.withMessageOn(
                0,
                this.promptStage.requestContext.messages()
                                               .get(0)
                                               .withContent(new QuestionVisionRequest(message))
        );
    }

}
