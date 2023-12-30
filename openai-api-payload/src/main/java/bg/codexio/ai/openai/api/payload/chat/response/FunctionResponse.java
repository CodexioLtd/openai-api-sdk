package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

public record FunctionResponse(
        String name,
        String arguments
)
        implements Mergeable<FunctionResponse> {

    public static FunctionResponse empty() {
        return new FunctionResponse(
                null,
                null
        );
    }

    @Override
    public FunctionResponse merge(FunctionResponse other) {
        return new FunctionResponse(
                this.name() != null
                ? this.name()
                : other.name(),
                Objects.requireNonNullElse(
                        this.arguments(),
                        ""
                ) + Objects.requireNonNullElse(
                        other.arguments(),
                        ""
                )
        );
    }
}
