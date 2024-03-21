package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

import java.io.File;
import java.io.IOException;

/**
 * Synchronous context to choose between raw response, just
 * a simple string answer, or an image download
 */
public class SynchronousExecutor<R extends ImageRequest>
        extends ImageConfigurationStage<R>
        implements RuntimeExecutor {

    private final ImageDataResponse response;

    SynchronousExecutor(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        super(
                executor,
                builder
        );
        this.response = this.executor.immediate()
                                     .execute(this.builder.specificRequestCreator()
                                                          .apply(this.builder));
    }

    /**
     * Returns the whole DTO from the HTTP response that includes the
     * generated image and the additional information,
     * including the useful <b>revised prompt</b>.
     *
     * @return {@link ImageDataResponse}
     */
    public ImageDataResponse andGetRaw() {
        return this.response;
    }

    /**
     * Downloads the image from the successful HTTP response and returns the
     * {@link File} instance/s.
     *
     * @return {@link ImageDataResponse}
     */
    public File[] andDownload(File targetFolder) throws IOException {
        return DownloadExecutor.FromResponse.downloadTo(
                targetFolder,
                this.response,
                Format.fromVal(this.builder.responseFormat())
        );
    }
}
