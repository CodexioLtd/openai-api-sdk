package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

public final class FunctionResponse
        implements Mergeable<FunctionResponse> {
    private final String name;
    private final String arguments;

    public FunctionResponse(
            String name,
            String arguments
    ) {
        this.name = name;
        this.arguments = arguments;
    }

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

    public String name() {
        return name;
    }

    public String arguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (FunctionResponse) obj;
        return Objects.equals(this.name,
                              that.name) && Objects.equals(this.arguments,
                                                           that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,
                            arguments);
    }

    @Override
    public String toString() {
        return "FunctionResponse[" + "name=" + name + ", " + "arguments="
                + arguments + ']';
    }

}
