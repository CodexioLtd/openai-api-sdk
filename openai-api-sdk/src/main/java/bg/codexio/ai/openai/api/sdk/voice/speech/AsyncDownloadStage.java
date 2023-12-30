package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;

import java.io.File;

/**
 * Configures target folder
 */
public class AsyncDownloadStage
        extends SpeechConfigurationStage {

    AsyncDownloadStage(
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
     * @return {@link AsyncPromiseStage} to register callbacks
     */
    public AsyncPromiseStage whenDownloadedTo(File targetFolder) {
        return new AsyncPromiseStage(
                this.executor,
                this.requestBuilder,
                targetFolder
        );
    }
}
