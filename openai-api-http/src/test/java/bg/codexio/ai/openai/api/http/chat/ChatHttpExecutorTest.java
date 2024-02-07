package bg.codexio.ai.openai.api.http.chat;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.chat.response.ChatUsageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatHttpExecutorTest {

    private static final String URL = TEST_BASE_URL.concat("/chat/completions");
    private static final String JSON_NO_STREAM_REQUEST = "{\"model\":\"test"
            + "-ai-model\",\"logprobs\":false,\"stream\":false}";
    private static final String JSON_WITH_STREAM_REQUEST = "{\"model\":\"test"
            + "-ai-model\",\"logprobs\":false,\"stream\":true}";
    private static final ChatMessageRequest REQUEST_DTO =
            ChatMessageRequest.builder()
                                                                            .withModel("test-ai-model")
                                                                            .withMessages(null)
                                                                            .shouldStream(false)
                                                                            .build();
    private static final ChatMessageRequest STREAM_REQUEST_DTO =
            ChatMessageRequest.builder()
                                                                                   .withModel("test-ai-model")
                                                                                   .withMessages(null)
                                                                                   .shouldStream(true)
                                                                                   .build();
    private static final ChatMessageResponse OK_RESPONSE_DTO =
            new ChatMessageResponse(
            "1",
            null,
            0,
            "test-ai-model",
            null,
            null
    );
    private static final ChatMessageResponse STREAM_RESPONSE_DTO =
            new ChatMessageResponse(
            "1",
            null,
            1,
            "test-ai-model",
            new ChatUsageResponse(
                    0,
                    0,
                    0
            ),
            new ArrayList<>()
    );
    private static final String OK_RESPONSE_BODY =
            "{\"id\":\"1\"," + "\"created\":0,\"model\":\"test-ai-model\"}";
    private static final String STREAM_RESPONSE_BODY = "{\"id\":\"1\","
            + "\"created\":0,\"model\":\"test-ai-model\"}{\"id\":\"2\","
            + "\"created\":1,\"model\":\"test-ai-model\"}";
    private static final Supplier<Response> BASE_JSON_RESPONSE =
            () -> createOkResponse(
            URL,
            OK_RESPONSE_BODY.getBytes(),
            "application/json"
    );
    private static final Supplier<Response> STREAM_JSON_RESPONSE =
            () -> createOkResponse(
            URL,
            (
                    "data:{\"id\":\"1\",\"created\":0,"
                            + "\"model\":\"test-ai-model\"}\ndata:{\"id\":\"2"
                            + "\","
                            + "\"created\":1,\"model\":\"test-ai-model\"}\n"
                            + "[DONE]"
            ).getBytes(),
            "application/json"
    );
    private static final Supplier<Response> ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );

    private OkHttpClient client;
    private ChatHttpExecutor executor;

    @BeforeEach
    public void setUp() {
        this.client = Mockito.mock(OkHttpClient.class);
        initExecutor();
    }

    @Test
    public void testExecute_noError_shouldParseResponse() {
        ExecutorTests.testExecute_noError_shouldParseResponse(
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                BASE_JSON_RESPONSE.get(),
                REQUEST_DTO,
                OK_RESPONSE_DTO,
                this.executor
        );
    }

    @Test
    public void testExecute_withError_shouldThrowException() {
        ExecutorTests.testExecute_withResponseError_shouldThrowException(
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                ERROR_JSON_RESPONSE.get(),
                REQUEST_DTO,
                this.executor
        );
    }

    @Test
    public void testExecuteAsync_noError_shouldInvokeCallbacks() {
        ExecutorTests.testExecuteAsync_noError_shouldInvokeCallbacks(
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                BASE_JSON_RESPONSE.get(),
                REQUEST_DTO,
                OK_RESPONSE_BODY,
                OK_RESPONSE_DTO,
                this.executor
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testExecuteAsync_withConnectionError_shouldThrowException(boolean stream) {
        ExecutorTests.testExecuteAsync_withConnectionError_shouldThrowException(
                stream,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                JSON_WITH_STREAM_REQUEST,
                BASE_JSON_RESPONSE.get(),
                STREAM_REQUEST_DTO,
                REQUEST_DTO,
                this.executor
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testExecuteAsync_withResponseError_shouldThrowException(boolean stream) {
        ExecutorTests.testExecuteAsync_withResponseError_shouldThrowException(
                stream,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                JSON_WITH_STREAM_REQUEST,
                ERROR_JSON_RESPONSE.get(),
                STREAM_REQUEST_DTO,
                REQUEST_DTO,
                this.executor
        );
    }

    @Test
    public void testExecuteAsync_noErrorWithStream_shouldInvokeCallbacksAndMergeResponse() {
        ExecutorTests.testExecuteAsync_noErrorWithStream_shouldInvokeCallbacksAndMergeResponse(
                this.client,
                URL,
                JSON_WITH_STREAM_REQUEST,
                STREAM_JSON_RESPONSE.get(),
                this.executor,
                STREAM_REQUEST_DTO,
                STREAM_RESPONSE_BODY,
                STREAM_RESPONSE_DTO
        );
    }

    @Test
    public void testExecuteReactive_noErrorNoStream_shouldHaveRawLineFlux() {
        ExecutorTests.testExecuteReactive_noErrorNoStream_shouldHaveRawLineFlux(
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                BASE_JSON_RESPONSE.get(),
                this.executor,
                REQUEST_DTO,
                OK_RESPONSE_BODY
        );
    }

    @Test
    public void testExecuteReactive_noErrorNoStream_shouldHaveCorrectResponseMono() {
        ExecutorTests.testExecuteReactive_noErrorNoStream_shouldHaveCorrectResponseMono(
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                BASE_JSON_RESPONSE.get(),
                this.executor,
                REQUEST_DTO,
                OK_RESPONSE_DTO
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testExecuteReactive_withConnectionError_shouldThrowException(boolean stream) {
        ExecutorTests.testExecuteReactive_withConnectionError_shouldThrowException(
                stream,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                JSON_WITH_STREAM_REQUEST,
                BASE_JSON_RESPONSE.get(),
                this.executor,
                REQUEST_DTO,
                STREAM_REQUEST_DTO
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testExecuteReactive_withResponseError_shouldThrowException(boolean stream) {
        ExecutorTests.testExecuteReactive_withResponseError_shouldThrowException(
                stream,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                JSON_WITH_STREAM_REQUEST,
                ERROR_JSON_RESPONSE.get(),
                this.executor,
                REQUEST_DTO,
                STREAM_REQUEST_DTO
        );
    }

    @Test
    public void testExecuteReactive_noErrorWithStream_shouldMergeInFlux() {
        ExecutorTests.testExecuteReactive_noErrorWithStream_shouldMergeInFlux(
                this.client,
                URL,
                JSON_WITH_STREAM_REQUEST,
                STREAM_JSON_RESPONSE.get(),
                this.executor,
                STREAM_REQUEST_DTO,
                STREAM_RESPONSE_BODY
        );
    }

    @Test
    public void testExecuteReactive_noErrorWithStream_shouldMergeInMono() {
        ExecutorTests.testExecuteReactive_noErrorWithStream_shouldMergeInMono(
                this.client,
                URL,
                JSON_WITH_STREAM_REQUEST,
                STREAM_JSON_RESPONSE.get(),
                this.executor,
                STREAM_REQUEST_DTO,
                STREAM_RESPONSE_DTO
        );
    }

    @Test
    public void testCanStream_withAllTrue_shouldBeTrue() {
        assertTrue(this.executor.canStream(STREAM_REQUEST_DTO));
    }

    @Test
    public void testCanStream_withRequestFalseApiTrue_shouldBeFalse() {
        assertFalse(this.executor.canStream(REQUEST_DTO));
    }

    private void initExecutor() {
        this.executor = new ChatHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }

}

