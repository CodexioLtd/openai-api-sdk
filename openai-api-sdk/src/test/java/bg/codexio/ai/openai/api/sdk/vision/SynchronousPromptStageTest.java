package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.response.ChatChoiceResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatUsageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SynchronousPromptStageTest {

    private SynchronousPromptStage promptStage;

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
        this.promptStage = new SynchronousPromptStage(
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
        var expectedRequest = this.expectWith("What’s in this image?");

        when(TEST_EXECUTOR.execute(eq(expectedRequest))).thenReturn(mockResponse());

        var result = this.promptStage.describe();

        assertEquals(
                "test-response",
                result
        );
    }

    @Test
    public void testDescribe_expectManualPrompt() {
        var expectedRequest = this.expectWith("What is this?");

        when(TEST_EXECUTOR.execute(eq(expectedRequest))).thenReturn(mockResponse());

        var result = this.promptStage.describe("What is this?");

        assertEquals(
                "test-response",
                result
        );
    }

    @Test
    public void testDescribeRaw_withoutPrompt_expectDefaultPrompt() {
        var expectedRequest = this.expectWith("What’s in this image?");

        when(TEST_EXECUTOR.execute(eq(expectedRequest))).thenReturn(mockResponse());

        var result = this.promptStage.describeRaw();

        assertEquals(
                mockResponse(),
                result
        );
    }

    @Test
    public void testDescribeRaw_expectManualPrompt() {
        var expectedRequest = this.expectWith("What is this?");

        when(TEST_EXECUTOR.execute(eq(expectedRequest))).thenReturn(mockResponse());

        var result = this.promptStage.describeRaw("What is this?");

        assertEquals(
                mockResponse(),
                result
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
