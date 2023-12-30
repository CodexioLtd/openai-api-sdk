package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

/**
 * Reactive context to choose if the response image should be automatically
 * downloaded or just delivered.
 * Strongly recommended to use this only if a true
 * reactive runtime is present, such as
 * <a href="https://github.com/reactor/reactor-netty">Reactor Netty</a>.
 */
public class ReactiveExecutor<R extends ImageRequest>
        extends ImageConfigurationStage<R>
        implements RuntimeExecutor {

    ReactiveExecutor(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        super(
                executor,
                builder
        );
    }

    /**
     * @return {@link Mono} of the {@link ImageDataResponse} of the response
     * received from the HTTP request
     */
    public Mono<ImageDataResponse> get() {
        return this.executor.executeReactive(this.builder.specificRequestCreator()
                                                         .apply(this.builder))
                            .response();
    }

    /**
     * @param targetFolder the desired location for downloading the file from
     *                     the response
     * @return {@link Flux} of the {@link File} that has been saved in the
     * provided location
     */
    public Flux<File> download(File targetFolder) {
        return this.executor.executeReactive(this.builder.specificRequestCreator()
                                                         .apply(this.builder))
                            .response()
                            .flatMapMany(response -> Flux.fromIterable(response.data()))
                            .handle((image, sink) -> {
                                try {
                                    sink.next(DownloadExecutor.FromFile.downloadFile(
                                            targetFolder,
                                            Format.fromVal(this.builder.responseFormat()),
                                            image
                                    ));
                                } catch (IOException e) {
                                    sink.error(new RuntimeException(e));
                                }
                            });
    }
}
