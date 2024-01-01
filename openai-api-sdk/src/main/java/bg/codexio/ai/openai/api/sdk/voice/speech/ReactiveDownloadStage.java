package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import reactor.core.publisher.Mono;

import java.io.File;

/**
 * Configures target folder
 */
public class ReactiveDownloadStage
        extends SpeechConfigurationStage {

    ReactiveDownloadStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures target folder to download to
     *
     * @param targetFolder {@link File} folder where to download the produced
     *                     audio file
     * @return {@link Mono<File>} to subscribe when the file is downloaded
     */
    public Mono<File> downloadTo(File targetFolder) {
        return this.executor.executeReactive(this.requestBuilder.build())
                .getResponse()
                            .handle((response, sink) -> {
                                try {
                                    sink.next(DownloadExecutor.downloadTo(
                                            targetFolder,
                                            response,
                                            this.requestBuilder.responseFormat()
                                    ));
                                } catch (Exception e) {
                                    sink.error(e);
                                }
                            });
    }

}
