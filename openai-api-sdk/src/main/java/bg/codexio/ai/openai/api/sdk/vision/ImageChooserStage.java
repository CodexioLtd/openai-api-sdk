package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;
import bg.codexio.ai.openai.api.payload.vision.request.MessageContentHolder;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

import java.io.File;
import java.util.List;

/**
 * Configures image either as a file or base64 encoded one
 */
public class ImageChooserStage
        extends VisionConfigurationStage {

    private final int index;

    ImageChooserStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        this(
                executor,
                requestContext,
                0
        );
    }

    ImageChooserStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext,
            int index
    ) {
        super(
                executor,
                requestContext
        );
        this.index = index;
    }

    /**
     * Adds image as a local file
     *
     * @return {@link ImageDetailStage} to input how much detail into the
     * analyzing to put
     */
    public ImageDetailStage explain(File image) {
        return this.explain(ImageUrlRequest.fromLocalFile(image));
    }

    /**
     * Adds image as a URL or Base64 encoded string
     *
     * @return {@link ImageDetailStage} to input how much detail into the
     * analyzing to put
     */
    public ImageDetailStage explain(String urlOrBase64) {
        return this.explain(new ImageUrlRequest(
                urlOrBase64,
                null
        ));
    }

    private ImageDetailStage explain(ImageUrlRequest request) {
        return new ImageDetailStage(
                this.executor,
                this.requestContext.withMessages(new MessageContentHolder(List.of(new ImageUrlMessageRequest(request)))),
                this.index
        );
    }
}
