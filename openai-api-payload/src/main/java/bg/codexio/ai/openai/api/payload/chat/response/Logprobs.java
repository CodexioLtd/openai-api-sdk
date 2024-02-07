package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.List;
import java.util.Objects;

public record Logprobs(
        List<Content> content
)
        implements Mergeable<Logprobs> {

    @Override
    public Logprobs merge(Logprobs other) {
        return new Logprobs(Objects.requireNonNullElse(
                this.content,
                other.content
        ));
    }
}