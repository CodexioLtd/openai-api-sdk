package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.message.chat.MessageContentStage;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadAsyncRuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadImmediateRuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadReactiveRuntimeExecutor;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class ThreadChatStage {

    private final RuntimeSelectionStage runtimeSelection;

    public ThreadChatStage(RuntimeSelectionStage runtimeSelection) {
        this.runtimeSelection = runtimeSelection;
    }

    public MessageContentStage immediate() {
        if (this.runtimeSelection.immediate() instanceof ThreadImmediateRuntimeExecutor executor) {
            return Messages.defaults(executor.finish())
                           .and()
                           .chat();
        }

        return null;
    }

    public void async(Consumer<MessageContentStage> consumer) {
        if (this.runtimeSelection.async() instanceof ThreadAsyncRuntimeExecutor executor) {
            executor.finishRaw()
                    .then(threadId -> consumer.accept(Messages.defaults(threadId)
                                                              .and()
                                                              .chat()));
        }
    }

    public Mono<MessageContentStage> reactive() {
        if (this.runtimeSelection.reactive() instanceof ThreadReactiveRuntimeExecutor executor) {
            return executor.finish()
                           .handle((response, sink) -> sink.next(Messages.defaults(response)
                                                                         .and()
                                                                         .chat()));
        }

        return Mono.empty();
    }
}