package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;

import java.util.function.Consumer;

/**
 * Registers callbacks and sends request
 */
public class AsyncPromiseStage
        extends TranslationConfigurationStage
        implements bg.codexio.ai.openai.api.sdk.AsyncPromiseStage<SpeechTextResponse> {


    AsyncPromiseStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
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
        this.executor.executeAsync(
                this.requestBuilder.build(),
                onEachLine,
                afterAll
        );
    }
}
