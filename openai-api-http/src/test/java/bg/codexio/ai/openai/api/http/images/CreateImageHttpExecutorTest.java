package bg.codexio.ai.openai.api.http.images;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.payload.images.response.ImageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateImageHttpExecutorTest {

    private static final String URL = TEST_BASE_URL.concat("/images/generations");
    private static final String JSON_NO_STREAM_REQUEST =
            "{\"prompt\":\"test" + "-prompt\",\"model\":\"test-model\",\"n\":1,"
                    + "\"quality\":\"test-quality\","
                    + "\"response_format\":\"test-format\","
                    + "\"size\":\"test-size\","
                    + "\"style\":\"test-style\",\"user\":\"test-user\"}";
    private static final CreateImageRequest REQUEST_DTO =
            new CreateImageRequest(
            "test-prompt",
            "test-model",
            1,
            "test-quality",
            "test-format",
            "test-size",
            "test-style",
            "test-user"
    );
    private static final ImageDataResponse OK_RESPONSE_DTO =
            new ImageDataResponse(
            Long.parseLong("0"),
            Collections.singletonList(new ImageResponse(
                    "test-base64",
                    "test-url",
                    "test-revised-prompt"
            ))
    );
    private static final String OK_RESPONSE_BODY = "{\"created\":0,"
            + "\"data\":[{\"b64_json\":\"test-base64\",\"url\":\"test-url\","
            + "\"revised_prompt\":\"test-revised-prompt\"}]}";
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
    private CreateImageHttpExecutor executor;

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
        this.executor = new CreateImageHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }

}
