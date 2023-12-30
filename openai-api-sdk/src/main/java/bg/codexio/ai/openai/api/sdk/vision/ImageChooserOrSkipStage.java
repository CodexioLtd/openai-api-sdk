package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

import java.io.File;

/**
 * Either inputs more images and goes back to
 * {@link ImageDetailStage} or goes further to
 * select a runtime.
 */
public class ImageChooserOrSkipStage
        extends VisionConfigurationStage {
    private final int messageIndex;

    ImageChooserOrSkipStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext,
            int messageIndex
    ) {
        super(
                executor,
                requestContext
        );
        this.messageIndex = messageIndex;
    }

    /**
     * @see ImageChooserStage#explain(String)
     */
    public ImageDetailStage explainAnother(String urlOrBase64) {
        return this.explain(new ImageUrlRequest(
                urlOrBase64,
                null
        ));
    }

    /**
     * @see ImageChooserStage#explain(File)
     */
    public ImageDetailStage explainAnother(File image) {
        return this.explain(ImageUrlRequest.fromLocalFile(image));
    }

    /**
     * Finishes adding images for explanation and goes on the next stage
     *
     * @return {@link VisionRuntimeSelectionStage} to select a runtime
     */
    public VisionRuntimeSelectionStage andRespond() {
        return new VisionRuntimeSelectionStage(
                this.executor,
                this.requestContext
        );
    }

    private ImageDetailStage explain(ImageUrlRequest imageRequest) {
        return new ImageDetailStage(
                this.executor,
                this.requestContext.withMessageOn(
                        0,
                        this.requestContext.messages()
                                           .get(0)
                                           .withContent(new ImageUrlMessageRequest(imageRequest))
                ),
                this.messageIndex
        );
    }

}
