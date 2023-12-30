package bg.codexio.ai.openai.api.http.vision;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VisionHttpExecutorTest {

    private static final String URL = "http://base-url/chat/completions";
    private static final String JSON_NO_STREAM_REQUEST =
            "{\"model\":\"test" + "-ai-model\",\"messages\":[]}";
    private static final VisionRequest REQUEST_DTO = VisionRequest.empty()
                                                                  .withModel(
                                                                          "test-ai-model");
    private static final ChatMessageResponse OK_RESPONSE_DTO =
            new ChatMessageResponse(
            "1",
            null,
            0,
            "test-ai-model",
            null,
            null
    );
    private static final String OK_RESPONSE_BODY =
            "{\"id\":\"1\"," + "\"created\":0,\"model\":\"test-ai-model\"}";
    private static final Supplier<Response> BASE_JSON_RESPONSE =
            () -> createOkResponse(
            URL,
            OK_RESPONSE_BODY.getBytes(),
            "application/json"
    );
    private static final Supplier<Response> ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );

    private OkHttpClient client;
    private VisionHttpExecutor executor;

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

    @Test
    public void testExecuteAsync_withConnectionError_shouldThrowException() {
        ExecutorTests.testExecuteAsync_withConnectionError_shouldThrowException(
                false,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                null,
                BASE_JSON_RESPONSE.get(),
                null,
                REQUEST_DTO,
                this.executor
        );
    }

    @Test
    public void testExecuteAsync_withResponseError_shouldThrowException() {
        ExecutorTests.testExecuteAsync_withResponseError_shouldThrowException(
                false,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                null,
                ERROR_JSON_RESPONSE.get(),
                null,
                REQUEST_DTO,
                this.executor
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

    @Test
    public void testExecuteReactive_withConnectionError_shouldThrowException() {
        ExecutorTests.testExecuteReactive_withConnectionError_shouldThrowException(
                false,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                null,
                BASE_JSON_RESPONSE.get(),
                this.executor,
                REQUEST_DTO,
                null
        );
    }

    @Test
    public void testExecuteReactive_withResponseError_shouldThrowException() {
        ExecutorTests.testExecuteReactive_withResponseError_shouldThrowException(
                false,
                this.client,
                URL,
                JSON_NO_STREAM_REQUEST,
                null,
                ERROR_JSON_RESPONSE.get(),
                this.executor,
                REQUEST_DTO,
                null
        );
    }

    @Test
    public void testCanStream_shouldBeFalse() {
        assertFalse(this.executor.canStream(REQUEST_DTO));
    }

    private void initExecutor() {
        this.executor = new VisionHttpExecutor(
                this.client,
                "http://base-url",
                new ObjectMapper()
        );
    }

}

