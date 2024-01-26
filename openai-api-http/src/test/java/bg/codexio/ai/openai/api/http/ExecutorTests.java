package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.http.exception.HttpCallFailedException;
import bg.codexio.ai.openai.api.http.exception.OpenAIRespondedNot2xxException;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.error.ErrorResponse;
import bg.codexio.ai.openai.api.payload.error.ErrorResponseHolder;
import okhttp3.*;
import okio.Buffer;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class ExecutorTests {

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecute_noError_shouldParseResponse(
            OkHttpClient client,
            String url,
            String requestBody,
            Response okHttpResponse,
            I requestDto,
            O responseDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var response = executor.execute(requestDto);

        assertEquals(
                responseDto,
                response
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteWithPathVariable_noError_shouldParseResponse(
            OkHttpClient client,
            String url,
            String pathVariable,
            String requestBody,
            Response okHttpResponse,
            I requestDto,
            O responseDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                String.format(
                        url,
                        pathVariable
                ),
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var response = executor.executeWithPathVariable(
                requestDto,
                pathVariable
        );

        assertEquals(
                responseDto,
                response
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteWithPathVariable_withResponseError_shouldThrowException(
            OkHttpClient client,
            String url,
            String pathVariable,
            String requestBody,
            Response okHttpResponse,
            I requestDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                String.format(
                        url,
                        pathVariable
                ),
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var exception = assertThrows(
                OpenAIRespondedNot2xxException.class,
                () -> executor.executeWithPathVariable(
                        requestDto,
                        pathVariable
                )
        );

        assertMessageAndStatus(exception);
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecute_withResponseError_shouldThrowException(
            OkHttpClient client,
            String url,
            String requestBody,
            Response okHttpResponse,
            I requestDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var exception = assertThrows(
                OpenAIRespondedNot2xxException.class,
                () -> executor.execute(requestDto)
        );

        assertMessageAndStatus(exception);
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecute_withCallError_shouldThrowException(
            OkHttpClient client,
            String url,
            String requestBody,
            I requestDto,
            E executor
    ) {
        doAnswer((Answer<Void>) invocation -> {
            throw new IOException("Exception");
        }).when(client)
          .newCall(requestEq(
                  url,
                  requestBody
          ));

        assertThrows(
                HttpCallFailedException.class,
                () -> executor.execute(requestDto)
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteAsync_noError_shouldInvokeCallbacks(
            OkHttpClient client,
            String url,
            String requestBody,
            Response okHttpResponse,
            I requestDto,
            String responseBody,
            O responseDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var lines = new StringBuilder();
        Consumer<String> lineCb = lines::append;

        var response = (O[]) new Mergeable[1];
        Consumer<O> finalizer = r -> response[0] = r;

        executor.executeAsync(
                requestDto,
                lineCb,
                finalizer
        );

        assertAll(
                () -> assertEquals(
                        responseBody,
                        lines.toString()
                ),
                () -> assertEquals(
                        responseDto,
                        response[0]
                )
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteAsync_withConnectionError_shouldThrowException(
            boolean stream,
            OkHttpClient client,
            String url,
            String requestBody,
            String streamRequestBody,
            Response okHttpResponse,
            I streamRequestDto,
            I requestDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody,
                streamRequestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                true
        ));

        Consumer<String> lineCb = x -> {
        };
        Consumer<O> finalizer = r -> {
        };

        var exception = assertThrows(
                HttpCallFailedException.class,
                () -> executor.executeAsync(
                        stream
                        ? streamRequestDto
                        : requestDto,
                        lineCb,
                        finalizer
                )
        );

        assertEquals(
                String.format(
                        "HTTP call to %s failed.",
                        url
                ),
                exception.getMessage()
        );
    }


    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteAsync_withResponseError_shouldThrowException(
            boolean stream,
            OkHttpClient client,
            String url,
            String requestBody,
            String streamRequestBody,
            Response okHttpResponse,
            I streamRequestDto,
            I requestDto,
            E executor
    ) {
        when(client.newCall(requestEq(
                url,
                streamRequestBody,
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        Consumer<String> lineCb = x -> {
        };
        Consumer<O> finalizer = r -> {
        };

        var exception = assertThrows(
                OpenAIRespondedNot2xxException.class,
                () -> executor.executeAsync(
                        stream
                        ? streamRequestDto
                        : requestDto,
                        lineCb,
                        finalizer
                )
        );

        assertMessageAndStatus(exception);
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteAsync_noErrorWithStream_shouldInvokeCallbacksAndMergeResponse(
            OkHttpClient client,
            String url,
            String streamRequestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            String streamResponseBody,
            O streamResponseDto
    ) {
        when(client.newCall(requestEq(
                url,
                streamRequestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var lines = spy(new StringBuilder());
        Consumer<String> lineCb = lines::append;

        var response = (O[]) new Mergeable[1];
        Consumer<O> finalizer = r -> response[0] = r;

        executor.executeAsync(
                requestDto,
                lineCb,
                finalizer
        );

        assertAll(
                () -> verify(
                        lines,
                        times(2)
                ).append(anyString()),
                () -> assertEquals(
                        streamResponseBody,
                        lines.toString()
                ),
                () -> assertEquals(
                        streamResponseDto,
                        response[0]
                )
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteReactive_noErrorNoStream_shouldHaveRawLineFlux(
            OkHttpClient client,
            String url,
            String requestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            String responseBody
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var execution = executor.executeReactive(requestDto);

        assertEquals(
                responseBody,
                execution.lines()
                         .blockFirst()
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteReactive_noErrorNoStream_shouldHaveCorrectResponseMono(
            OkHttpClient client,
            String url,
            String requestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            O responseDto
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var execution = executor.executeReactive(requestDto);

        assertEquals(
                responseDto,
                execution.response()
                         .block()
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteReactive_withConnectionError_shouldThrowException(
            boolean stream,
            OkHttpClient client,
            String url,
            String requestBody,
            String streamRequestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            I streamRequestDto
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody,
                streamRequestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                true
        ));

        var execution = executor.executeReactive(stream
                                                 ? streamRequestDto
                                                 : requestDto);

        var exception = assertThrows(
                HttpCallFailedException.class,
                () -> execution.lines()
                               .blockFirst()
        );

        assertEquals(
                String.format(
                        "HTTP call to %s failed.",
                        url
                ),
                exception.getMessage()
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteReactive_withResponseError_shouldThrowException(
            boolean stream,
            OkHttpClient client,
            String url,
            String requestBody,
            String streamRequestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            I streamRequestDto
    ) {
        when(client.newCall(requestEq(
                url,
                requestBody,
                streamRequestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var execution = executor.executeReactive(stream
                                                 ? streamRequestDto
                                                 : requestDto);

        var exception = assertThrows(
                OpenAIRespondedNot2xxException.class,
                () -> execution.lines()
                               .blockFirst()
        );

        assertMessageAndStatus(exception);
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteReactive_noErrorWithStream_shouldMergeInFlux(
            OkHttpClient client,
            String url,
            String streamRequestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            String streamResponseBody
    ) {
        when(client.newCall(requestEq(
                url,
                streamRequestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var execution = executor.executeReactive(requestDto);

        assertEquals(
                streamResponseBody,
                execution.lines()
                         .reduce((a, b) -> a + b)
                         .block()
        );
    }

    public static <I extends Streamable, O extends Mergeable<O>,
            E extends DefaultOpenAIHttpExecutor<I, O>> void testExecuteReactive_noErrorWithStream_shouldMergeInMono(
            OkHttpClient client,
            String url,
            String streamRequestBody,
            Response okHttpResponse,
            E executor,
            I requestDto,
            O streamResponseDto
    ) {
        when(client.newCall(requestEq(
                url,
                streamRequestBody
        ))).thenReturn(new MockCall(
                okHttpResponse,
                false
        ));

        var execution = executor.executeReactive(requestDto);

        assertEquals(
                streamResponseDto,
                execution.response()
                         .block()
        );
    }

    private static void assertMessageAndStatus(OpenAIRespondedNot2xxException exception) {
        assertAll(
                () -> assertEquals(
                        new ErrorResponseHolder(new ErrorResponse(
                                "Test Error",
                                null,
                                null,
                                null
                        )),
                        exception.getErrorHolder()
                ),
                () -> assertEquals(
                        400,
                        exception.getHttpStatusCode()
                )
        );
    }

    private static Request requestEq(
            String url,
            String... body
    ) {
        return argThat(request -> {
            if (!request.url()
                        .toString()
                        .equals(url)) {
                return false;
            }

            for (var b : body) {
                try (var buffer = new Buffer()) {
                    request.newBuilder()
                           .build()
                           .body()
                           .writeTo(buffer);
                    var str = buffer.readUtf8();

                    if (str.replace(
                                   "\r\n",
                                   "\n"
                           )
                           .trim()
                           .equals(b)) {
                        return true;
                    }
                } catch (IOException e) {
                    return false;
                }
            }

            return false;
        });
    }

    public static Response createOkResponse(
            String url,
            byte[] body,
            String mediaType
    ) {
        return new Response.Builder().message("OK")
                                     .body(ResponseBody.create(
                                             body,
                                             MediaType.parse(mediaType)
                                     ))
                                     .protocol(Protocol.HTTP_1_1)
                                     .code(200)
                                     .request(fromPair(
                                             url,
                                             new String(body)
                                     ))
                                     .build();
    }

    public static Response createErrorResponse(
            String url,
            byte[] body,
            String mediaType
    ) {
        return new Response.Builder().message("Bad Request")
                                     .body(ResponseBody.create(
                                             body,
                                             MediaType.parse(mediaType)
                                     ))
                                     .protocol(Protocol.HTTP_1_1)
                                     .code(400)
                                     .request(fromPair(
                                             url,
                                             new String(body)
                                     ))
                                     .build();
    }

    public static Request fromPair(
            String url,
            String body
    ) {
        return new Request.Builder().url(url)
                                    .post(RequestBody.create(
                                            body,
                                            MediaType.parse("application/json")
                                    ))
                                    .build();

    }

    public static void setMultipartBoundary(
            DefaultOpenAIHttpExecutor<?, ?> executor,
            String boundary
    ) {
        executor.setMultipartBoundary(boundary);
    }

    public record MockCall(
            Response execute,
            boolean shouldFail
    )
            implements Call {


        @Override
        public void cancel() {

        }

        @NotNull
        @Override
        public Call clone() {
            return this;
        }

        @Override
        public void enqueue(@NotNull Callback callback) {
            if (this.shouldFail) {
                callback.onFailure(
                        this,
                        new IOException()
                );
            } else {
                try {
                    callback.onResponse(
                            this,
                            this.execute
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public boolean isExecuted() {
            return false;
        }


        @NotNull
        @Override
        public Timeout timeout() {
            return Timeout.NONE;
        }

        @NotNull
        @Override
        public Request request() {
            return null;
        }
    }

}

