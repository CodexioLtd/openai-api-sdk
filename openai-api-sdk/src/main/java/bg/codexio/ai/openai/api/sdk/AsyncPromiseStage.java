package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;

import java.util.function.Consumer;

public interface AsyncPromiseStage<T extends Mergeable<T>> {

    /**
     * Sends request in asynchronous fashion,
     * registers a callback to be called when response arrives.
     *
     * @param afterAll a callback which accepts the {@link SpeechTextResponse}
     */
    default void then(Consumer<T> afterAll) {
        this.then(
                x -> {},
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
    default void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {}
        );
    }

    /**
     * Sends request in asynchronous fashion,
     * registers callbacks to be called when response arrives and
     * on each line of the response
     *
     * @param onEachLine a callback which accepts the String line of the
     *                   response
     * @param afterAll   a callback which accepts the {@link T}
     */
    void then(
            Consumer<String> onEachLine,
            Consumer<T> afterAll
    );
}
