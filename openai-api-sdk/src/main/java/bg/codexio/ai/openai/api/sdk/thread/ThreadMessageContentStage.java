package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.thread.constant.ThreadDefaultValuesConstants.MESSAGE_SENDER_ROLE;

public class ThreadMessageContentStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {

    ThreadMessageContentStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadAdvancedConfigurationStage<R> startWith(String... content) {
        return new ThreadAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.withMessages(Arrays.stream(content)
                                                       .map(message -> MessageRequest.builder()
                                                                                     .withContent(message)
                                                                                     .withRole(MESSAGE_SENDER_ROLE)
                                                                                     .build())
                                                       .toList())
        );
    }

    public ThreadMessageFileStage<R> startWith(String content) {
        return new ThreadMessageFileStage<>(
                this.httpExecutor,
                this.requestBuilder,
                content
        );
    }
}