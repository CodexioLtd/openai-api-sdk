package bg.codexio.ai.openai.api.http.images;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.payload.images.response.ImageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Collections;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EditImageHttpExecutorTest {

    private static final String URL = "http://base-url/images/edits";
    private static final String MULTIPART_NO_STREAM_REQUEST =
            "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"image\"; "
                    + "filename=\"fake-image.png\"\n"
                    + "Content-Type: image/png\n" + "\n" + "test\n"
                    + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"prompt\"\n" + "\n"
                    + "test-prompt\n" + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"mask\"; "
                    + "filename=\"fake-image.png\"\n"
                    + "Content-Type: image/png\n" + "\n" + "test\n"
                    + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"model\"\n" + "\n"
                    + "test-model\n" + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"n\"\n" + "\n"
                    + "1\n" + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"size\"\n" + "\n"
                    + "test-size\n" + "--test-boundary\n"
                    + "Content-Disposition: form-data; "
                    + "name=\"response_format\"\n" + "\n"
                    + "test-response-format\n" + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"user\"\n" + "\n"
                    + "test-user\n" + "--test-boundary--";
    private static final EditImageRequest REQUEST_DTO = new EditImageRequest(
            new File(EditImageHttpExecutorTest.class.getClassLoader()
                                                    .getResource("fake-image.png")
                                                    .getPath()),
            "test-prompt",
            new File(EditImageHttpExecutorTest.class.getClassLoader()
                                                    .getResource("fake-image.png")
                                                    .getPath()),
            "test-model",
            1,
            "test-size",
            "test-response-format",
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
    private EditImageHttpExecutor executor;

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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
                MULTIPART_NO_STREAM_REQUEST,
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
        this.executor = new EditImageHttpExecutor(
                this.client,
                "http://base-url",
                new ObjectMapper()
        );

        ExecutorTests.setMultipartBoundary(
                this.executor,
                "test-boundary"
        );
    }

}
