package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.http.exception.HttpCallFailedException;
import bg.codexio.ai.openai.api.http.exception.UnparseableRequestException;
import bg.codexio.ai.openai.api.http.exception.UnparseableResponseException;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;
import static bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables.OPENAI_LOGGING_ENABLED;
import static bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables.OPENAI_LOGGING_LEVEL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DefaultOpenAIHttpExecutorTest {

    private static final String URL = "http://base-url/some/resource";
    private static final String JSON_NO_STREAM_REQUEST =
            "{\"name\":\"test\"," + "\"stream\":false}";
    private static final String JSON_WITH_STREAM_REQUEST =
            "{\"name\":\"test" + "\",\"stream\":true}";
    private static final MockRequest REQUEST_DTO = new MockRequest(
            "test",
            false
    );
    private static final MockRequest STREAM_REQUEST_DTO = new MockRequest(
            "test",
            true
    );
    private static final MockResponse OK_RESPONSE_DTO = new MockResponse(
            1,
            "test"
    );
    private static final MockResponse STREAM_RESPONSE_DTO = new MockResponse(
            2,
            "testmore"
    );
    private static final String OK_RESPONSE_BODY =
            "{\"id\":1," + "\"name\":\"test\"}";
    private static final String STREAM_RESPONSE_BODY =
            "{\"id\":1," + "\"name\":\"test\"}{\"id\":2,\"name\":\"more\"}";
    private static final Supplier<Response> BASE_JSON_RESPONSE =
            () -> createOkResponse(
            URL,
            "{\"id\":1,\"name\":\"test\"}".getBytes(),
            "application/json"
    );
    private static final Supplier<Response> STREAM_JSON_RESPONSE =
            () -> createOkResponse(
            URL,
            (
                    "data:{\"id\":1,\"name\":\"test\"}\ndata:{\"id\":2,"
                            + "\"name\":\"more\"}\n[DONE]"
            ).getBytes(),
            "application/json"
    );
    private static final Supplier<Response> ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );

    private Logger logger;
    private LoggingEventBuilder logEvent;

    private OkHttpClient client;
    private DefaultOpenAIHttpExecutor<MockRequest, MockResponse> executor;

    @BeforeEach
    public void setUp() {
        this.logger = mock(Logger.class);
        this.logEvent = mock(LoggingEventBuilder.class);
        this.client = Mockito.mock(OkHttpClient.class);
        doReturn(this.logEvent).when(this.logger)
                               .atLevel(any(Level.class));
        initExecutor(true);
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
    public void testExecute_withResponseError_shouldThrowException() {
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
    public void testExecute_withCallError_shouldThrowException() {
        ExecutorTests.testExecute_withCallError_shouldThrowException(
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
        assertTrue(this.executor.canStream(new MockRequest(
                "test",
                true
        )));
    }

    @Test
    public void testCanStream_withRequestFalseApiTrue_shouldBeFalse() {
        assertFalse(this.executor.canStream(new MockRequest(
                "test",
                false
        )));
    }

    @Test
    public void testCanStream_withRequestFalseApiFalse_shouldBeFalse() {
        initExecutor(false);

        assertFalse(this.executor.canStream(new MockRequest(
                "test",
                false
        )));
    }

    @Test
    public void testCanStream_withRequestTrueApiFalse_shouldBeFalse() {
        initExecutor(false);

        assertFalse(this.executor.canStream(new MockRequest(
                "test",
                true
        )));
    }

    @Test
    public void testGetField_withCorrectFieldAndObject_shouldReturnFieldValue()
            throws NoSuchFieldException {
        var obj = new MockRequest(
                "test-name",
                false
        );
        var field = MockRequest.class.getDeclaredField("name");
        field.setAccessible(true);

        var result = DefaultOpenAIHttpExecutor.getField(
                field,
                obj,
                String.class
        );

        assertEquals(
                "test-name",
                result
        );
    }

    @Test
    public void testGetField_withMismatchingFieldClassPair_shouldThrowException()
            throws NoSuchFieldException {
        var obj = new MockRequest(
                "test-name",
                false
        );
        var field = MockResponse.class.getDeclaredField("name");
        field.setAccessible(true);

        assertThrows(
                RuntimeException.class,
                () -> DefaultOpenAIHttpExecutor.getField(
                        field,
                        obj,
                        String.class
                )
        );
    }

    @Test
    public void testLog_enabledWithLevel_shouldLogAtLevel() {
        this.executor = spy(this.executor);
        doReturn("true").when(this.executor)
                        .getEnv(eq(OPENAI_LOGGING_ENABLED.name()));
        doReturn("inFo").when(this.executor)
                        .getEnv(eq(OPENAI_LOGGING_LEVEL.name()));

        try (var uuidUtils = mockStatic(UUID.class)) {
            var uuid = mock(UUID.class);
            doReturn("test-execution").when(uuid)
                                      .toString();
            uuidUtils.when(UUID::randomUUID)
                     .thenReturn(uuid);

            this.executor.reinitializeExecutionIdentification();
            this.executor.log(
                    "test-{}",
                    "message"
            );

            var expectedLogMessage = "[test-execution]: test-{}";

            assertAll(
                    () -> verify(
                            this.logger,
                            times(1)
                    ).atLevel(eq(Level.INFO)),
                    () -> verify(
                            this.logEvent,
                            times(1)
                    ).log(
                            eq(expectedLogMessage),
                            (Object[]) eq(new String[]{"message"})
                    )
            );
        }
    }

    @Test
    public void testLog_enabledWithoutLevel_shouldLogAtDebugLevel() {
        this.executor = spy(this.executor);
        doReturn("true").when(this.executor)
                        .getEnv(eq(OPENAI_LOGGING_ENABLED.name()));

        try (var uuidUtils = mockStatic(UUID.class)) {
            var uuid = mock(UUID.class);
            doReturn("test-execution").when(uuid)
                                      .toString();
            uuidUtils.when(UUID::randomUUID)
                     .thenReturn(uuid);

            this.executor.reinitializeExecutionIdentification();
            this.executor.log(
                    "test-{}",
                    "message"
            );

            var expectedLogMessage = "[test-execution]: test-{}";

            verify(
                    this.logger,
                    times(1)
            ).debug(
                    eq(expectedLogMessage),
                    (Object[]) eq(new String[]{"message"})
            );
        }
    }

    @Test
    public void testLog_disabled_nothingHappens() {
        this.executor = spy(this.executor);
        doReturn(null).when(this.executor)
                      .getEnv(eq(OPENAI_LOGGING_ENABLED.name()));

        this.executor.log(
                "test-{}",
                "message"
        );

        verifyNoInteractions(
                this.logger,
                this.logEvent
        );
    }

    @Test
    public void testToError_correctError_correctParse() {
        var response = "{\"error\": null}";
        var result = this.executor.toError(response);

        assertAll(
                () -> assertNotNull(result),
                () -> assertNull(result.error())
        );
    }

    @Test
    public void testToError_incorrectError_shouldThrowException() {
        var response = "error";
        assertThrows(
                UnparseableResponseException.class,
                () -> this.executor.toError(response)
        );
    }

    @Test
    public void testToResponse_correctResponse_correctParse() {
        var response = "{\"name\": null}";
        var result = this.executor.toResponse(response);

        assertAll(
                () -> assertNotNull(result),
                () -> assertNull(result.name())
        );
    }

    @Test
    public void testToResponse_incorrectJson_shouldThrowException() {
        var response = "response";
        assertThrows(
                UnparseableResponseException.class,
                () -> this.executor.toResponse(response)
        );
    }

    @Test
    public void testToResponse_correctOkHttpResponse_shouldCallStringToResponse()
            throws IOException {
        var okHttpResponse = mock(Response.class);
        var body = mock(ResponseBody.class);
        doReturn("{\"name\": \"test\"}").when(body)
                                        .string();
        doReturn(body).when(okHttpResponse)
                      .body();

        var result = this.executor.toResponse(okHttpResponse);

        assertEquals(
                new MockResponse(
                        0,
                        "test"
                ),
                result
        );
    }

    @Test
    public void testToResponse_okHttpThrowsIO_shouldRethrow()
            throws IOException {
        var okHttpResponse = mock(Response.class);
        var body = mock(ResponseBody.class);
        doThrow(new IOException()).when(body)
                                  .string();
        doReturn(body).when(okHttpResponse)
                      .body();

        var exception = assertThrows(
                HttpCallFailedException.class,
                () -> this.executor.toResponse(okHttpResponse)
        );

        assertEquals(
                "HTTP call to http://base-url/some/resource failed.",
                exception.getMessage()
        );
    }

    @Test
    public void testToJson_correctRequest_expectJson() {
        var result = this.executor.toJson(REQUEST_DTO);
        assertEquals(
                JSON_NO_STREAM_REQUEST,
                result
        );
    }

    @Test
    public void testToJson_cyclicRequest_expectException() {
        var enhancedExecutor = new DefaultOpenAIHttpExecutor<MockFormData,
                MockResponse>(
                this.client,
                "http://base-url",
                new ObjectMapper(),
                MockResponse.class,
                "/some/resource",
                false,
                this.logger
        ) {};

        assertThrows(
                UnparseableRequestException.class,
                () -> enhancedExecutor.toJson(new MockFormData(
                        50,
                        new File(this.getClass()
                                     .getResource("/")
                                     .getFile() + "fake-image.png"),
                        new MockRequest(
                                "test-name",
                                false
                        )
                ))
        );
    }

    /**
     * There's no example in OpenAI API for nested requests,
     * the main assumption here seems wrong, and it can be adapted,
     * once such scenario becomes real.
     */
    @Test
    public void testToFormData_nestedObjects_shouldPopulateAll()
            throws IOException {
        this.executor.setMultipartBoundary("test-boundary");

        var body = this.executor.toFormData(new MockFormData(
                50,
                new File(this.getClass()
                             .getResource("/")
                             .getFile() + "fake-image.png"),
                new MockRequest(
                        "test-name",
                        false
                )
        ));

        var buffer = new Buffer();
        body.writeTo(buffer);

        var result = buffer.readUtf8()
                           .replace(
                                   "\r\n",
                                   "\n"
                           );

        assertEquals(
                "--test-boundary\n" +
                        "Content-Disposition: form-data; name=\"id\"\n" +
                        "\n" +
                        "50\n" +
                        "--test-boundary\n" +
                        "Content-Disposition: form-data; name=\"img\"; filename=\"fake-image.png\"\n" +
                        "Content-Type: application/octet-stream\n" +
                        "\n" +
                        "test\n" +
                        "--test-boundary\n" +
                        "Content-Disposition: form-data; name=\"name\"\n" +
                        "\n" +
                        "test-name\n" +
                        "--test-boundary\n" +
                        "Content-Disposition: form-data; name=\"stream\"\n" +
                        "\n" +
                        "false\n" +
                        "--test-boundary\n" +
                        "Content-Disposition: form-data; name=\"id\"\n" +
                        "\n" +
                        "50\n" +
                        "--test-boundary\n" +
                        "Content-Disposition: form-data; name=\"img\"; filename=\"fake-image.png\"\n" +
                        "Content-Type: application/octet-stream\n" +
                        "\n" +
                        "test\n" +
                        "--test-boundary--\n",
                result
        );

    }

    private void initExecutor(boolean streamable) {
        this.executor = new DefaultOpenAIHttpExecutor<>(
                this.client,
                "http://base-url",
                new ObjectMapper(),
                MockResponse.class,
                "/some/resource",
                streamable,
                this.logger
        ) {};
    }


    static class MockRequest implements Streamable {
        private final String name;
        private final boolean stream;

        public MockRequest(String name, boolean stream) {
            this.name = name;
            this.stream = stream;
        }

        public String getName() {
            return name;
        }

        public boolean isStream() {
            return stream;
        }

        @Override
        public boolean stream() {
            return stream;
        }
    }

    static class MockFormData implements Streamable {
        private final int id;
        private final File img;
        private final File nullImg;
        private final MockRequest nested;
        private final MockFormData self;
        private final MockFormData nullSelf;

        public MockFormData(int id, File img, MockRequest nested) {
            this.id = id;
            this.img = img;
            this.nullImg = null;
            this.nested = nested;
            this.self = this;
            this.nullSelf = null;
        }

        public int getId() {
            return id;
        }

        public File getImg() {
            return img;
        }

        public File getNullImg() {
            return nullImg;
        }

        public MockRequest getNested() {
            return nested;
        }

        public MockFormData getSelf() {
            return self;
        }

        public MockFormData getNullSelf() {
            return nullSelf;
        }

        @Override
        public boolean stream() {
            return false;
        }
    }

    static class MockResponse implements Mergeable<MockResponse> {
        private final int id;
        private final String name;

        public MockResponse() {
            this(0, null);
        }

        public MockResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonProperty
        public int id() {
            return id;
        }

        @JsonProperty
        public String name() {
            return name;
        }

        @Override
        public MockResponse merge(MockResponse other) {
            return new MockResponse(
                    Math.max(this.id, other.id),
                    this.name + other.name
            );
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            MockResponse that = (MockResponse) obj;
            return id == that.id && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.id, this.name);
        }
    }


}

