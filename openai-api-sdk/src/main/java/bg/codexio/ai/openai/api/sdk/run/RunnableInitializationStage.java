package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
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

    public RunnableInitializationStage initialize(AssistantResponse assistantResponse) {
        return this.processRunnableAssistantInitialization(
                assistantResponse,
                this::performInitialization
        );
    }

    public RunnableInitializationStage initialize(String assistantId) {
        return this.performInitialization(assistantId);
    }

    public RunnableAdvancedConfigurationStage deepConfigure(AssistantResponse assistantResponse) {
        return this.processRunnableAssistantInitialization(
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

    public RunnableRuntimeSelectionStage andRespond() {
        return new RunnableRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    private <T> T processRunnableAssistantInitialization(
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

    private RunnableInitializationStage performInitialization(String assistantId) {
        return new RunnableInitializationStage(
                this.httpExecutor,
                this.requestBuilder.withAssistantId(assistantId),
                this.threadId
        );
    }
}