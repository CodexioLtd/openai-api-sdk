package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

public record Content(
        String token,
        Double logprob,
        byte[] bytes
)
        implements Mergeable<Content> {

    public static Content empty() {
        return new Content(
                null,
                null,
                new byte[0]
        );
    }

    @Override
    public Content merge(Content other) {
        if (Objects.isNull(other)) {
            return this;
        }

        return new Content(
                Objects.requireNonNullElse(
                        this.token,
                        other.token
                ),
                Objects.requireNonNullElse(
                               this.logprob,
                               0
                       )
                       .doubleValue() + Objects.requireNonNullElse(
                                                       other.logprob,
                                                       0
                                               )
                                               .doubleValue(),

                Objects.requireNonNullElse(
                        this.bytes(),
                        other.bytes()
                )
        );
    }
}