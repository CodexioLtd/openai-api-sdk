package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import okhttp3.Request;

import java.io.IOException;

public class DefaultSynchronousHttpExecutor<I extends Streamable,
        O extends Mergeable<O>>
        implements SynchronousHttpExecutor<I, O> {

    private final DefaultOpenAIHttpExecutor<I, O> defaultOpenAIHttpExecutor;

    public DefaultSynchronousHttpExecutor(DefaultOpenAIHttpExecutor<I, O> defaultOpenAIHttpExecutor) {
        this.defaultOpenAIHttpExecutor = defaultOpenAIHttpExecutor;
    }

    @Override
    public O execute(I request) {
        var httpRequest =
                this.defaultOpenAIHttpExecutor.prepareRequest(request);

        return this.performRequestExecution(httpRequest);
    }

    @Override
    public O executeWithPathVariable(
            I request,
            String pathVariable
    ) {
        var httpRequest =
                this.defaultOpenAIHttpExecutor.prepareRequestWithPathVariable(
                request,
                pathVariable
        );

        return this.performRequestExecution(httpRequest);
    }

    @Override
    public O retrieve(String... pathVariables) {
        var httpRequest =
                this.defaultOpenAIHttpExecutor.prepareRequestWithPathVariables(pathVariables);

        return this.performRequestExecution(httpRequest);
    }

    private O performRequestExecution(Request httpRequest) {
        try (
                var httpResponse =
                        this.defaultOpenAIHttpExecutor.client.newCall(httpRequest)
                                                                        .execute()
        ) {
            this.defaultOpenAIHttpExecutor.throwOnError(httpResponse);

            return this.defaultOpenAIHttpExecutor.toResponse(httpResponse);
        } catch (IOException e) {
            throw this.defaultOpenAIHttpExecutor.createHttpCallFailedException(
                    httpRequest,
                    e
            );
        }
    }
}