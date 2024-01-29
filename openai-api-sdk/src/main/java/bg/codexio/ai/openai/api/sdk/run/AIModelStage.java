package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;

public class AIModelStage
        extends RunnableConfigurationStage {

    AIModelStage(
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

    public RunnableAdvancedConfigurationStage poweredBy(ModelType modelType) {
        return new RunnableAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withModel(modelType.name()),
                this.threadId
        );
    }

    public RunnableAdvancedConfigurationStage poweredByGPT35() {
        return this.poweredBy(new GPT35TurboModel());
    }

    public RunnableAdvancedConfigurationStage poweredByGPT40() {
        return this.poweredBy(new GPT40Model());
    }

    public RunnableAdvancedConfigurationStage turboPowered() {
        return this.poweredBy(new GPT401106Model());
    }
}
