package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DefaultAsynchronousHttpExecutor<I extends Streamable,
        O extends Mergeable<O>>
        implements AsynchronousHttpExecutor<I, O> {

    private final DefaultOpenAIHttpExecutor<I, O> defaultOpenAIHttpExecutor;

    public DefaultAsynchronousHttpExecutor(DefaultOpenAIHttpExecutor<I, O> defaultOpenAIHttpExecutor) {
        this.defaultOpenAIHttpExecutor = defaultOpenAIHttpExecutor;
    }

    @Override
    public void execute(
            I request,
            Consumer<String> callBack,
            Consumer<O> finalizer
    ) {
        this.makeAsyncRequest(
                this.defaultOpenAIHttpExecutor.prepareRequest(request),
                request,
                callBack,
                finalizer
        );
    }

    @Override
    public void executeWithPathVariable(
            I request,
            String pathVariable,
            Consumer<String> callBack,
            Consumer<O> finalizer
    ) {
        this.makeAsyncRequest(
                this.defaultOpenAIHttpExecutor.prepareRequestWithPathVariable(
                        request,
                        pathVariable
                ),
                request,
                callBack,
                finalizer
        );
    }

    @Override
    public void retrieve(
            Consumer<String> callBack,
            Consumer<O> finalizer,
            String... pathVariables
    ) {
        this.makeAsyncRequest(
                this.defaultOpenAIHttpExecutor.prepareRequestWithPathVariables(pathVariables),
                null,
                callBack,
                finalizer
        );
    }

    private void makeAsyncRequest(
            Request httpRequest,
            I request,
            Consumer<String> callBack,
            Consumer<O> finalizer
    ) {
        this.defaultOpenAIHttpExecutor.client.newCall(httpRequest)
                                             .enqueue(new Callback() {
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
                                                     var responseContent =
                                                             new ArrayList<O>();
                                                     var content =
                                                             new StringBuilder();
                                                     try (var httpResponseBody = response.body()) {
                                                         if (Objects.isNull(request)) {
                                                             readResponseBody(
                                                                     content,
                                                                     callBack,
                                                                     httpResponseBody
                                                             );
                                                         } else {
                                                             readResponseBody(
                                                                     request,
                                                                     responseContent,
                                                                     content,
                                                                     callBack,
                                                                     httpResponseBody,
                                                                     response
                                                             );
                                                         }
                                                     }

                                                     if (Objects.isNull(request)) {
                                                         consumeResponseBodyWithoutStreaming(
                                                                 finalizer,
                                                                 content
                                                         );
                                                     } else {
                                                         if (defaultOpenAIHttpExecutor.canStream(request)) {
                                                             consumeResponseBodyWithStreaming(
                                                                     finalizer,
                                                                     responseContent
                                                             );
                                                         } else {
                                                             consumeResponseBodyWithoutStreaming(
                                                                     finalizer,
                                                                     content
                                                             );
                                                         }
                                                     }
                                                 }
                                             });
    }

    private void readResponseBody(
            I request,
            List<O> responseContent,
            StringBuilder content,
            Consumer<String> callBack,
            ResponseBody httpResponseBody,
            Response response
    ) throws IOException {
        defaultOpenAIHttpExecutor.throwOnError(response);

        var reader =
                new BufferedReader(new InputStreamReader(httpResponseBody.byteStream()));
        var line = (String) null;
        while ((
                line = reader.readLine()
        ) != null) {
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

            callBack.accept(line);
            if (defaultOpenAIHttpExecutor.canStream(request)) {
                responseContent.add(defaultOpenAIHttpExecutor.toResponse(line));
            } else {
                content.append(line);
            }
        }
    }

    private void readResponseBody(
            StringBuilder content,
            Consumer<String> callBack,
            ResponseBody httpResponseBody
    ) throws IOException {
        var reader =
                new BufferedReader(new InputStreamReader(httpResponseBody.byteStream()));
        var line = (String) null;
        while ((
                line = reader.readLine()
        ) != null) {
            callBack.accept(line);
            content.append(line);
        }
    }

    private void consumeResponseBodyWithoutStreaming(
            Consumer<O> finalizer,
            StringBuilder content
    ) {
        finalizer.accept(defaultOpenAIHttpExecutor.toResponse(content.toString()));
    }

    private void consumeResponseBodyWithStreaming(
            Consumer<O> finalizer,
            List<O> responseContent
    ) {
        finalizer.accept(responseContent.stream()
                                        .reduce(Mergeable::doMerge)
                                        .orElse(null));
    }
}