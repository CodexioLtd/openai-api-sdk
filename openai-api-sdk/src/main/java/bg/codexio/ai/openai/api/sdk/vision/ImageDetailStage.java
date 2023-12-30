package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.DetailedAnalyze;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

/**
 * Configures the {@link DetailedAnalyze}
 */
public class ImageDetailStage
        extends VisionConfigurationStage {

    private final int index;


    ImageDetailStage(
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
     * Adds {@link DetailedAnalyze} to the request
     *
     * @return {@link ImageChooserOrSkipStage} to choose another image or go
     * next
     */
    public ImageChooserOrSkipStage analyze(DetailedAnalyze analyze) {
        return new ImageChooserOrSkipStage(
                this.executor,
                this.requestContext.withMessageOn(
                        0,
                        this.requestContext.messages()
                                           .get(0)
                                           .withContentOn(
                                                   this.index,
                                                   new ImageUrlMessageRequest((
                                                                                      (ImageUrlMessageRequest) this.requestContext.messages()
                                                                                                                                  .get(0)
                                                                                                                                  .content()
                                                                                                                                  .get(index)
                                                                              ).imageUrl()
                                                                               .withDetail(analyze.val())

                                                   )
                                           )
                ),
                this.index + 1
        );
    }
}
