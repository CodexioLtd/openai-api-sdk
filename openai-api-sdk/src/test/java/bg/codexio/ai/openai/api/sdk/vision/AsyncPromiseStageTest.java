package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.response.ChatChoiceResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatUsageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncExecution;
import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AsyncPromiseStageTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ChatMessageResponse RESPONSE_DTO = mockResponse();
    private static final String RAW_RESPONSE;

    static {
        try {
            RAW_RESPONSE = OBJECT_MAPPER.writeValueAsString(RESPONSE_DTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private AsyncPromise promiseStage;

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
                        0,
                        null
                ))
        );
    }

    @BeforeEach
    public void setUp() {
        this.promiseStage = new AsyncPromise(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
                             .withTokens(TEST_TOKENS)
                             .withMessages(new MessageContentHolder(List.of(
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             TEST_IMAGE,
                                             TEST_ANALYZE
                                     )),
                                     new QuestionVisionRequest("What is this?")
                             )))
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedWithCorrectResponse() {
        mockAsyncExecution(
                TEST_EXECUTOR,
                RESPONSE_DTO,
                RAW_RESPONSE
        );
        var callbackData = prepareCallback(ChatMessageResponse.class);

        this.promiseStage.then(callbackData.callback());

        assertEquals(
                RESPONSE_DTO,
                callbackData.data()
        );
    }

    @Test
    public void testThen_withTwoCallbacks_shouldBeInvokedWithCorrectResponseAndLines() {
        mockAsyncExecution(
                TEST_EXECUTOR,
                RESPONSE_DTO,
                RAW_RESPONSE
        );
        var lineData = prepareCallback(String.class);
        var finalizerData = prepareCallback(ChatMessageResponse.class);

        this.promiseStage.then(
                finalizerData.callback(),
                lineData.callback()
        );

        assertAll(
                () -> assertEquals(
                        RESPONSE_DTO,
                        finalizerData.data()
                ),
                () -> verify(
                        lineData.callback(),
                        times(2)
                ).accept(any())
        );
    }

    @Test
    public void testOnEachLine_withTwoLinesOfResponse_shouldBeCalledTwice() {
        mockAsyncExecution(
                TEST_EXECUTOR,
                RESPONSE_DTO,
                RAW_RESPONSE
        );
        var lineData = prepareCallback(String.class);

        this.promiseStage.onEachLine(lineData.callback());

        verify(
                lineData.callback(),
                times(2)
        ).accept(any());
    }
}
