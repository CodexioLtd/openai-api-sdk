package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

public record ToolCallResponse(
        Integer index,
        String id,
        String type,
        FunctionResponse function

)
        implements Mergeable<ToolCallResponse> {

    public static ToolCallResponse empty() {
        return new ToolCallResponse(
                null,
                null,
                null,
                FunctionResponse.empty()
        );
    }

    @Override
    public ToolCallResponse merge(ToolCallResponse other) {
        return new ToolCallResponse(
                this.index() != null
                ? this.index()
                : other.index(),
                this.id() != null
                ? this.id()
                : other.id(),
                this.type() != null
                ? this.type()
                : other.type(),
                Objects.requireNonNullElse(
                               this.function(),
                               FunctionResponse.empty()
                       )
                       .merge(Objects.requireNonNullElse(
                               other.function(),
                               FunctionResponse.empty()
                       ))
        );
    }
}
