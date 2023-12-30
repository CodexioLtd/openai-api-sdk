package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

public class VisionConfigurationStage {

    protected final VisionHttpExecutor executor;
    protected final VisionRequest requestContext;

    VisionConfigurationStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        this.executor = executor;
        this.requestContext = requestContext;
    }
}
