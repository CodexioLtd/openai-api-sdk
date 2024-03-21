package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Registers callbacks
 */
public class AsyncPromiseStage
        extends SpeechConfigurationStage {

    private final File targetFolder;
    private final Logger log;


    AsyncPromiseStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder,
            File targetFolder,
            Logger log
    ) {
        super(
                executor,
                requestBuilder
        );
        this.targetFolder = targetFolder;
        this.log = log;
    }

    AsyncPromiseStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder,
            File targetFolder
    ) {
        this(
                executor,
                requestBuilder,
                targetFolder,
                LoggerFactory.getLogger(AsyncPromiseStage.class)
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when file is downloaded.
     *
     * @param afterAll a callback which accepts the downloaded audio
     *                 {@link File}
     */
    public void then(Consumer<File> afterAll) {
        this.then(
                x -> {
                },
                afterAll,
                error -> log.error(
                        "Download error.",
                        error
                )
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when file is downloaded.
     *
     * @param afterAll a callback which accepts the downloaded audio
     *                 {@link File}
     * @param onError  a callback which will be invoked if an error occurs
     */
    public void then(
            Consumer<File> afterAll,
            Consumer<Throwable> onError
    ) {
        this.then(
                x -> {
                },
                afterAll,
                onError
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when String/byteArray line
     * of the response is received.
     *
     * @param onEachLine a callback which accepts a String line of the response
     */
    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {
                },
                error -> log.error(
                        "Download error.",
                        error
                )
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when String/byteArray line
     * of the response is received.
     *
     * @param onEachLine a callback which accepts a String line of the response
     * @param onError    a callback which will be invoked if an error occurs
     */
    public void onEachLine(
            Consumer<String> onEachLine,
            Consumer<Throwable> onError
    ) {
        this.then(
                onEachLine,
                x -> {
                },
                onError
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when file is downloaded and
     * a callback to be called when String/byteArray line
     *
     * @param onEachLine a callback which accepts a String line of the response
     * @param afterAll   a callback which accepts the downloaded audio
     *                   {@link File}
     * @param onError    a callback which will be invoked if an error occurs
     */
    public void then(
            Consumer<String> onEachLine,
            Consumer<File> afterAll,
            Consumer<Throwable> onError
    ) {
        this.executor.async()
                     .execute(
                             this.requestBuilder.build(),
                             onEachLine,
                             response -> CompletableFuture.supplyAsync(() -> {
                                                              try {
                                                                  return DownloadExecutor.downloadTo(
                                                                          targetFolder,
                                                                          response,
                                                                          this.requestBuilder.responseFormat()
                                                                  );
                                                              } catch (IOException e) {
                                                                  throw new RuntimeException(e);
                                                              }
                                                          })
                                                          .whenComplete((result, error) -> {
                                                              if (error
                                                                      != null) {
                                                                  onError.accept(error);
                                                              } else {
                                                                  afterAll.accept(result);
                                                              }
                                                          })
                     );
    }

    File getTargetFolder() {
        return targetFolder;
    }
}
