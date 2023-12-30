package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;

import java.util.function.Consumer;

/**
 * Registers callbacks and sends request
 */
public class AsyncPromiseStage
        extends TranscriptionConfigurationStage {

    AsyncPromiseStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when response arrives.
     *
     * @param afterAll a callback which accepts the {@link SpeechTextResponse}
     */
    public void then(Consumer<SpeechTextResponse> afterAll) {
        this.then(
                x -> {
                },
                afterAll
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called on each line of the response
     *
     * @param onEachLine a callback which accepts the String line of the
     *                   response
     */
    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {
                }
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers callbacks to be called when response arrives and
     * on each line of the response
     *
     * @param onEachLine a callback which accepts the String line of the
     *                   response
     * @param afterAll   a callback which accepts the {@link SpeechTextResponse}
     */
    public void then(
            Consumer<String> onEachLine,
            Consumer<SpeechTextResponse> afterAll
    ) {
        this.executor.executeAsync(
                this.requestBuilder.build(),
                onEachLine,
                afterAll
        );
    }
}
