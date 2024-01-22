package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.MetadataUtils;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public record ThreadRequestBuilder<R extends ThreadRequest>(
        Function<ThreadRequestBuilder<R>, R> specificRequestCreator,
        List<MessageRequest> messages,
        Map<String, String> metadata
) {

    public static <R extends ThreadRequest> ThreadRequestBuilder<R> builder() {
        return new ThreadRequestBuilder<>(
                null,
                null,
                null
        );
    }

    public ThreadRequestBuilder<R> withSpecificRequestCreator(Function<ThreadRequestBuilder<R>, R> specificRequestCreator) {
        return new ThreadRequestBuilder<>(
                specificRequestCreator,
                null,
                null
        );
    }

    public ThreadRequestBuilder<R> withMessages(List<MessageRequest> messages) {
        return new ThreadRequestBuilder<R>(
                specificRequestCreator,
                messages,
                metadata
        );
    }

    public ThreadRequestBuilder<R> addMessage(MessageRequest message) {
        var messages = new ArrayList<>(Objects.requireNonNullElse(
                this.messages,
                new ArrayList<MessageRequest>()
        ));
        messages.add(message);

        return this.withMessages(messages);
    }

    public ThreadRequestBuilder<R> withMetadata(Map<String, String> metadata) {
        return new ThreadRequestBuilder<R>(
                specificRequestCreator,
                messages,
                metadata
        );
    }

    public ThreadRequestBuilder<R> addMetadata(String... metadata) {
        var threadMetadata = MetadataUtils.addMetadata(
                this.metadata,
                metadata
        );

        return this.withMetadata(threadMetadata);
    }

    public ThreadRequestBuilder<R> build() {
        return new ThreadRequestBuilder<R>(
                specificRequestCreator,
                messages,
                metadata
        );
    }
}
