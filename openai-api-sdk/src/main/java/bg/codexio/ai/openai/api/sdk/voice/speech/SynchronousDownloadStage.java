package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;

import java.io.File;
import java.io.IOException;

/**
 * Sends blocking requests to OpenAI API
 */
public class SynchronousDownloadStage
        extends SpeechConfigurationStage {

    SynchronousDownloadStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Executes the request so far in synchronous blocking fashion,
     * the result of the OpenAI API response is then downloaded to the
     * supplied folder.
     *
     * @param targetFolder {@link File} folder where to download the produced
     *                     audio file
     * @return the downloaded audio {@link File}
     * @throws IOException if the target folder is not accessible
     */
    public File downloadTo(File targetFolder) throws IOException {
        return DownloadExecutor.downloadTo(
                targetFolder,
                this.executor.execute(this.requestBuilder.build()),
                this.requestBuilder.responseFormat()
        );
    }
}
