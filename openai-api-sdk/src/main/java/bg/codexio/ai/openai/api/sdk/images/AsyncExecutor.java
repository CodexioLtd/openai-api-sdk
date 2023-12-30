package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Promise abstraction.
 * Accepts callbacks which will be called
 * when response is received.
 */
public class AsyncExecutor<R extends ImageRequest>
        extends ImageConfigurationStage<R>
        implements RuntimeExecutor {

    private final R imageRequest;
    private final Logger log = LoggerFactory.getLogger(AsyncExecutor.class);
    private final Consumer<String> onEachLine = x -> {};

    AsyncExecutor(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        super(
                executor,
                builder
        );
        this.imageRequest = this.builder.specificRequestCreator()
                                        .apply(this.builder);
    }

    /**
     * Subscribe to the result of the HTTP request
     *
     * @param callback a consumer function that receives
     *                 {@link ImageDataResponse} as a parameter
     */
    public void onResponse(Consumer<ImageDataResponse> callback) {
        this.executor.executeAsync(
                this.imageRequest,
                this.onEachLine,
                callback
        );
    }

    /**
     * Subscribe to the completion of the download of the image in the response.
     *
     * @param targetFolder the desired location for downloading the file from
     *                     the response
     * @param callback     a consumer function to be executed on successful
     *                     download that receives {@link ImageDataResponse}
     *                     as a parameter
     *                     In this case we have a default error handler that
     *                     simply logs the error data
     */
    public void whenDownloaded(
            File targetFolder,
            Consumer<File[]> callback
    ) {
        this.whenDownloaded(
                targetFolder,
                callback,
                error -> log.error(
                        "Download error occurred",
                        error
                )
        );
    }

    /**
     * Subscribe to the completion of the download of the image in the response.
     *
     * @param targetFolder the desired location for downloading the file from
     *                     the response
     * @param callback     a consumer function to be executed on successful
     *                     download that receives {@link ImageDataResponse}
     *                     as a parameter
     * @param errorHandler a consumer function that to be executed on
     *                     unsuccessful download that receives
     *                     {@link Throwable} as a parameter
     */
    public void whenDownloaded(
            File targetFolder,
            Consumer<File[]> callback,
            Consumer<Throwable> errorHandler
    ) {
        this.executor.executeAsync(
                this.imageRequest,
                this.onEachLine,
                response -> CompletableFuture.supplyAsync(() -> {
                                                 try {
                                                     return DownloadExecutor.FromResponse.downloadTo(
                                                             targetFolder,
                                                             response,
                                                             Format.fromVal(this.builder.responseFormat())
                                                     );
                                                 } catch (IOException e) {
                                                     throw new RuntimeException(e);
                                                 }
                                             })
                                             .whenComplete((result, error) -> {
                                                 if (error != null) {
                                                     errorHandler.accept(error);
                                                 } else {
                                                     callback.accept(result);
                                                 }
                                             })
        );
    }

}
