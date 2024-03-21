package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.sdk.ObjectMapperSubtypesRegistrationUtils;
import bg.codexio.ai.openai.api.sdk.ThreadOperationUtils;
import bg.codexio.ai.openai.api.sdk.run.constant.RunnableDefaultValuesConstants;
import bg.codexio.ai.openai.api.sdk.run.constant.RunnableEnvironmentVariableNameConstants;
import bg.codexio.ai.openai.api.sdk.run.constant.RunnableStatusConstants;
import bg.codexio.ai.openai.api.sdk.run.exception.NonInitializedRunnableException;

import java.util.Objects;
import java.util.Optional;

public class RunnableResultStage
        extends RunnableConfigurationStage {

    private final RunnableResponse run;

    RunnableResultStage(
            RunnableHttpExecutor httpExecutor,
            RunnableRequest.Builder requestBuilder,
            String threadId,
            RunnableResponse run
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
        this.run = run;
    }

    public RunnableResponse waitForCompletionRaw() {
        return Optional.ofNullable(this.run)
                       .map(this::sleepThenRefresh)
                       .orElseThrow(NonInitializedRunnableException::new);
    }

    public RunnableResponse waitForCompletionRaw(RunnableResponse run) {
        return this.sleepThenRefresh(run);
    }

    public RunnableAdvancedConfigurationStage waitForCompletion() {
        return new RunnableAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder,
                this.waitForCompletionRaw(this.run)
                    .threadId()
        );
    }

    public String waitForCompletion(RunnableResponse run) {
        return this.sleepThenRefresh(run)
                   .threadId();
    }

    // issue with registration the assistant tools implementation to the
    // object mapper, while only assistant id as
    // parameter is used
    public RunnableResponse from(String runnableId) {
        return this.httpExecutor.immediate()
                                .retrieve(
                                        this.threadId,
                                        runnableId
                                );
    }

    public RunnableResponse from(RunnableResponse runnableResponse) {
        if (!runnableResponse.tools()
                             .isEmpty()) {
            ObjectMapperSubtypesRegistrationUtils.registerAssistantTools(
                    this.httpExecutor,
                    runnableResponse.tools()
            );
            return this.httpExecutor.immediate()
                                    .retrieve(
                                            runnableResponse.threadId(),
                                            runnableResponse.id()
                                    );
        }

        return this.httpExecutor.immediate()
                                .retrieve(
                                        runnableResponse.threadId(),
                                        runnableResponse.id()
                                );
    }

    // same issue here
    public RunnableResponse from(
            String threadId,
            String runnableId
    ) {
        return this.httpExecutor.immediate()
                                .retrieve(
                                        threadId,
                                        runnableId
                                );
    }

    private RunnableResponse sleepThenRefresh(RunnableResponse run) {
        while (Objects.equals(
                run.status(),
                RunnableStatusConstants.QUEUED
        ) || Objects.equals(
                run.status(),
                RunnableStatusConstants.IN_PROGRESS
        )) {
            try {
                ThreadOperationUtils.sleep(this.getCompletionSleepDuration());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            run = this.from(run);
        }

        return run;
    }

    private Long getCompletionSleepDuration() {
        return Optional.ofNullable(System.getenv(RunnableEnvironmentVariableNameConstants.COMPLETION_SLEEP_DURATION))
                       .map(Long::valueOf)
                       .orElse(RunnableDefaultValuesConstants.COMPLETION_SLEEP_DURATION_MILLIS);
    }
}