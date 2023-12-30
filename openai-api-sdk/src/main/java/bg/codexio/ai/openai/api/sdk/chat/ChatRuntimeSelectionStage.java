package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.sdk.Processing;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

/**
 * Configures how the HTTP client to act.
 * Whether the requests will be sent in an immediate blocking manner
 * (synchronous)
 * or they will be sent asynchronously via promises or reactive API.
 */
public class ChatRuntimeSelectionStage
        extends ChatConfigurationStage
        implements RuntimeSelectionStage {

    ChatRuntimeSelectionStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Requests will be synchronously blocking without streaming enabled.
     *
     * @return {@link ImmediateContextStage}
     */
    @Override
    public ImmediateContextStage immediate() {
        return this.immediate(Processing.DEFAULT);
    }

    /**
     * Requests will be synchronously blocking and might utilize
     * server-sent events (real-time streaming).
     *
     * @param processing {@link Processing} whether to use server-sent events
     * @return {@link ImmediateContextStage}
     */
    public ImmediateContextStage immediate(Processing processing) {
        return new ImmediateContextStage(
                this.executor,
                this.requestBuilder.shouldStream(processing.val())
        );
    }

    /**
     * Requests will be asynchronous with promises.
     * No real-time streaming will be enabled.
     *
     * @return {@link AsyncContextStage}
     */
    @Override
    public AsyncContextStage async() {
        return this.async(Processing.DEFAULT);
    }

    /**
     * Requests will be asynchronous with promises.
     * May utilize server-sent events (real-time streaming).
     *
     * @param processing {@link Processing} whether to use server-sent events
     * @return {@link AsyncContextStage}
     */
    public AsyncContextStage async(Processing processing) {
        return new AsyncContextStage(
                this.executor,
                this.requestBuilder.shouldStream(processing.val())
        );
    }

    /**
     * Requests will be asynchronous under reactive API.
     * Encouraged to be used only if a truly reactive
     * environment is present, such as
     * <a href="https://github.com/reactor/reactor-netty">Reactor Netty</a>.
     * No real-time streaming will be enabled.
     *
     * @return {@link ReactiveContextStage}
     */
    @Override
    public ReactiveContextStage reactive() {
        return this.reactive(Processing.DEFAULT);
    }

    /**
     * Requests will be asynchronous under reactive API.
     * Encouraged to be used only if a truly reactive
     * environment is present, such as
     * <a href="https://github.com/reactor/reactor-netty">Reactor Netty</a>.
     * May utilize server-sent events (real-time streaming).
     *
     * @param processing {@link Processing} whether to use server-sent events
     * @return {@link ReactiveContextStage}
     */
    public ReactiveContextStage reactive(Processing processing) {
        return new ReactiveContextStage(
                this.executor,
                this.requestBuilder.shouldStream(processing.val())
        );
    }
}
