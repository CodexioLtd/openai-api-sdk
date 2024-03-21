package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Predicate;

public class DefaultReactiveHttpExecutor<I extends Streamable,
        O extends Mergeable<O>>
        implements ReactiveHttpExecutor<I, O> {

    private final DefaultOpenAIHttpExecutor<I, O> defaultOpenAIHttpExecutor;

    public DefaultReactiveHttpExecutor(DefaultOpenAIHttpExecutor<I, O> defaultOpenAIHttpExecutor) {
        this.defaultOpenAIHttpExecutor = defaultOpenAIHttpExecutor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReactiveExecution<O> execute(I request) {
        return this.makeResponse(
                request,
                Flux.<String>create(sink -> this.defaultOpenAIHttpExecutor.client.newCall(this.defaultOpenAIHttpExecutor.prepareRequest(request))
                                                                                 .enqueue(this.makeReactiveRequest(
                                                                                         request,
                                                                                         sink
                                                                                 )))
                    .share()
        );
    }

    @Override
    public ReactiveExecution<O> executeWithPathVariable(
            I request,
            String pathVariable
    ) {
        return this.makeResponse(
                request,
                Flux.<String>create(sink -> this.defaultOpenAIHttpExecutor.client.newCall(this.defaultOpenAIHttpExecutor.prepareRequestWithPathVariable(
                                                        request,
                                                        pathVariable
                                                ))
                                                                                 .enqueue(this.makeReactiveRequest(
                                                                                         request,
                                                                                         sink
                                                                                 )))
                    .share()
        );
    }

    @Override
    public ReactiveExecution<O> retrieve(String... pathVariables) {
        return this.makeResponse(Flux.<String>create(sink -> this.defaultOpenAIHttpExecutor.client.newCall(this.defaultOpenAIHttpExecutor.prepareRequestWithPathVariables(pathVariables))
                                                                                                  .enqueue(this.makeReactiveRequest(
                                                                                                          null,
                                                                                                          sink
                                                                                                  )))
                                     .share());
    }

    private Callback makeReactiveRequest(
            I request,
            FluxSink<String> sink
    ) {
        return new Callback() {
            @Override
            public void onFailure(
                    @NotNull Call call,
                    @NotNull IOException e
            ) {
                throw defaultOpenAIHttpExecutor.createHttpCallFailedException(
                        call.request(),
                        e
                );
            }

            @Override
            public void onResponse(
                    @NotNull Call call,
                    @NotNull Response response
            ) throws IOException {
                try (var httpResponseBody = response.body()) {
                    try {
                        defaultOpenAIHttpExecutor.throwOnError(response);
                    } catch (Throwable throwable) {
                        sink.error(throwable);
                        return;
                    }

                    if (Objects.isNull(request)) {
                        readResponseBody(
                                httpResponseBody,
                                sink
                        );
                    } else {
                        readResponseBody(
                                request,
                                httpResponseBody,
                                sink
                        );
                    }
                } finally {
                    sink.complete();
                }
            }
        };
    }

    private void readResponseBody(
            I request,
            ResponseBody httpResponseBody,
            FluxSink<String> sink
    ) throws IOException {
        var reader =
                new BufferedReader(new InputStreamReader(httpResponseBody.byteStream()));
        var line = (String) null;
        while ((line = reader.readLine()) != null) {
            if (defaultOpenAIHttpExecutor.canStream(request)) {
                line = line.replace(
                                   "data:",
                                   ""
                           )
                           .trim();
                if (line.equals("[DONE]")) {
                    break;
                }
            }

            sink.next(line);
        }
    }

    private void readResponseBody(
            ResponseBody httpResponseBody,
            FluxSink<String> sink
    ) throws IOException {
        var reader =
                new BufferedReader(new InputStreamReader(httpResponseBody.byteStream()));
        var line = (String) null;
        while ((line = reader.readLine()) != null) {
            sink.next(line);
        }
    }

    private ReactiveExecution<O> makeResponse(
            I request,
            Flux<String> lines
    ) {
        if (this.defaultOpenAIHttpExecutor.canStream(request)) {
            var response = lines.collectList()
                                .mapNotNull(list -> list.stream()
                                                        .filter(Objects::nonNull)
                                                        .filter(Predicate.not(String::isBlank))
                                                        .map(this.defaultOpenAIHttpExecutor::toResponse)
                                                        .reduce(Mergeable::doMerge)
                                                        .orElse(null));

            return new ReactiveExecution<>(
                    lines,
                    response
            );
        }

        return this.makeResponse(lines);
    }

    private ReactiveExecution<O> makeResponse(
            Flux<String> lines
    ) {
        var response = lines.collectList()
                            .map(line -> String.join(
                                    "",
                                    line
                            ))
                            .map(this.defaultOpenAIHttpExecutor::toResponse);

        return new ReactiveExecution<>(
                lines,
                response
        );
    }
}