package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public class AIModelStage
        extends AssistantConfigurationStage {

    AIModelStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantToolStage poweredBy(ModelType modelType) {
        return new AssistantToolStage(
                this.httpExecutor,
                this.requestBuilder.withModel(modelType.name())
        );
    }

    public AssistantToolStage poweredByGPT35() {
        return this.poweredBy(new GPT35TurboModel());
    }

    public AssistantToolStage poweredByGPT40() {
        return this.poweredBy(new GPT40Model());
    }

    public AssistantToolStage turboPowered() {
        return this.poweredBy(new GPT401106Model());
    }
}