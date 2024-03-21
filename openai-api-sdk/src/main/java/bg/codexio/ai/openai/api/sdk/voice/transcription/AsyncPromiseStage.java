package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;

import java.util.function.Consumer;

/**
 * Registers callbacks and sends request
 */
public class AsyncPromiseStage
        extends TranscriptionConfigurationStage
        implements bg.codexio.ai.openai.api.sdk.AsyncPromiseStage<SpeechTextResponse> {

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
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<SpeechTextResponse> afterAll
    ) {
        this.executor.async()
                     .execute(
                             this.requestBuilder.build(),
                             onEachLine,
                             afterAll
                     );
    }
}
