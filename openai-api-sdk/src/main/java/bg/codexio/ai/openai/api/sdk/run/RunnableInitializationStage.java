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

    public RunnableResponse executeRaw(AssistantResponse assistantResponse) {
        return this.processAssistantResponse(
                assistantResponse,
                this::performExecution
        );
    }

    public RunnableResponse executeRaw(String assistantId) {
        return this.performExecution(assistantId);
    }

    public String execute(AssistantResponse assistantResponse) {
        return this.processAssistantResponse(
                           assistantResponse,
                           this::performExecution
                   )
                   .id();
    }

    // issue with registration the assistant tools implementation to the
    // object mapper, while only assistant id as
    // parameter is used
    public String execute(String assistantId) {
        return this.performExecution(assistantId)
                   .id();

    }

    public RunnableResultStage run(AssistantResponse assistantResponse) {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                this.processAssistantResponse(
                        assistantResponse,
                        this::performExecution
                )
        );
    }

    public RunnableResultStage run(String assistantID) {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                this.performExecution(assistantID)
        );
    }

    public RunnableResultStage messaging() {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                null
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

    private <T> T processAssistantResponse(
            AssistantResponse assistantResponse,
            Function<String, T> function
    ) {
        return Optional.of(assistantResponse)
                       .filter(assistant -> assistant.tools()
                                                     .isEmpty())
                       .map(AssistantResponse::id)
                       .map(function)
                       .orElseGet(() -> {
                           ObjectMapperSubtypesRegistrationUtils.registerAssistantTools(
                                   this.httpExecutor,
                                   assistantResponse.tools()
                           );

                           return function.apply(assistantResponse.id());
                       });
    }

    private RunnableResponse performExecution(String assistantId) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.withAssistantId(assistantId)
                                   .build(),
                this.threadId
        );
    }
}