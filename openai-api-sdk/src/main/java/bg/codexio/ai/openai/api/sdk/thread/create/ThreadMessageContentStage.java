package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.thread.constant.ThreadDefaultValuesConstants.MESSAGE_SENDER_ROLE;

public class ThreadMessageContentStage
        extends ThreadConfigurationStage {


    ThreadMessageContentStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadAdvancedConfigurationStage startWith(String... content) {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withMessages(Arrays.stream(content)
                                                       .map(message -> MessageRequest.builder()
                                                                                     .withContent(message)
                                                                                     .withRole(MESSAGE_SENDER_ROLE)
                                                                                     .build())
                                                       .toList())
        );
    }

    public ThreadMessageFileStage startWith(String content) {
        return new ThreadMessageFileStage(
                this.httpExecutor,
                this.requestBuilder,
                content
        );
    }
}