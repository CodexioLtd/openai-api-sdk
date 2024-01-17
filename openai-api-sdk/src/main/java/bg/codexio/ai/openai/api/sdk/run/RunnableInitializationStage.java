package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.sdk.ObjectMapperSubtypesRegistrationUtils;

import java.util.Optional;
import java.util.function.Function;

public class RunnableInitializationStage
        extends RunnableConfigurationStage {


    RunnableInitializationStage(
            RunnableHttpExecutor httpExecutor,
            RunnableRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public RunnableResponse run(AssistantResponse assistantResponse) {
        return this.processAssistantResponse(
                assistantResponse,
                this::run
        );
    }

    // issue with registration the assistant tools implementation to the
    // object mapper, while only assistant id as
    // parameter is used
    public RunnableResponse run(String assistantId) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.withAssistantId(assistantId)
                                   .build(),
                this.threadId
        );
    }

    public RunnableAdvancedConfigurationStage deepConfigure(AssistantResponse assistantResponse) {
        return this.processAssistantResponse(
                assistantResponse,
                this::deepConfigure
        );
    }

    // same issue here
    public RunnableAdvancedConfigurationStage deepConfigure(String assistantId) {
        return new RunnableAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withAssistantId(assistantId),
                this.threadId
        );
    }

    public RunnableResponse from(RunnableResponse runnableResponse) {
        if (!runnableResponse.tools()
                             .isEmpty()) {
            ObjectMapperSubtypesRegistrationUtils.registerAssistantTools(
                    this.httpExecutor,
                    runnableResponse.tools()
            );
            return this.httpExecutor.execute(
                    runnableResponse.threadId(),
                    runnableResponse.id()
            );
        }

        return this.httpExecutor.execute(
                runnableResponse.threadId(),
                runnableResponse.id()
        );
    }

    // same issue here
    public RunnableResponse from(
            String threadId,
            String runnableId
    ) {
        return this.httpExecutor.execute(
                threadId,
                runnableId
        );
    }

    private <T> T processAssistantResponse(
            AssistantResponse assistantResponse,
            Function<String, T> operation
    ) {
        return Optional.of(assistantResponse)
                       .filter(assistant -> assistant.tools()
                                                     .isEmpty())
                       .map(AssistantResponse::id)
                       .map(operation)
                       .orElseGet(() -> {
                           ObjectMapperSubtypesRegistrationUtils.registerAssistantTools(
                                   this.httpExecutor,
                                   assistantResponse.tools()
                           );

                           return operation.apply(assistantResponse.id());
                       });
    }
}