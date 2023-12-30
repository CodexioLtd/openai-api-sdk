package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

/**
 * Configures maxTokens
 */
public class TokenStage
        extends VisionConfigurationStage {


    TokenStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    /**
     * Configures maxTokens
     * So response will be limited to less than models default
     *
     * @return {@link ImageChooserStage} to choose image
     */
    public ImageChooserStage limitResponseTo(Integer tokens) {
        return new ImageChooserStage(
                this.executor,
                this.requestContext.withTokens(tokens)
        );
    }

    /**
     * Configures maxTokens
     * So response will be limited as much as the models default
     *
     * @return {@link ImageChooserStage} to choose image
     */
    public ImageChooserStage defaultTokens() {
        return this.limitResponseTo(null);
    }

    /**
     * Configures maxTokens
     * To the maximum possible
     *
     * @return {@link ImageChooserStage} to choose image
     */
    public ImageChooserStage maxTokens() {
        return this.limitResponseTo(4096);
    }

}
