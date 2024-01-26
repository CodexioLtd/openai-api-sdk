package bg.codexio.ai.openai.api.http.voice;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TranslationHttpExecutorTest {

    private static final String URL = TEST_BASE_URL.concat("/audio/translations");
    private static final String MULTIPART_NO_STREAM_REQUEST =
            "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"file\"; "
                    + "filename=\"fake-file.txt\"\n"
                    + "Content-Type: text/plain\n" + "\n" + "test\n" + "\n"
                    + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"model\"\n" + "\n"
                    + "test-ai-model\n" + "--test-boundary--";

    private static final TranslationRequest REQUEST_DTO =
            TranslationRequest.builder()
                                                                            .withFile(new File(TranslationHttpExecutorTest.class.getClassLoader()
                                                                                                                                .getResource("fake-file.txt")
                                                                                                                                .getPath()))
                                                                            .withModel("test-ai-model")
                                                                            .build();
    private static final SpeechTextResponse OK_RESPONSE_DTO =
            new SpeechTextResponse("test");
    private static final String OK_RESPONSE_BODY = "{\"text\":\"test\"}";
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
    private TranslationHttpExecutor executor;

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
        this.executor = new TranslationHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );

        this.executor.setMultipartBoundary("test-boundary");
    }

}

