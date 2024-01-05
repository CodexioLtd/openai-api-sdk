package bg.codexio.ai.openai.api.payload.chat.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ToolCallResponse
        implements Mergeable<ToolCallResponse> {
    private final Integer index;
    private final String id;
    private final String type;
    private final FunctionResponse function;

    public ToolCallResponse() {
        this(
                null,
                null,
                null,
                null
        );
    }

    public ToolCallResponse(
            Integer index,
            String id,
            String type,
            FunctionResponse function

    ) {
        this.index = index;
        this.id = id;
        this.type = type;
        this.function = function;
    }

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

    @JsonProperty
    public Integer index() {
        return index;
    }

    @JsonProperty
    public String id() {
        return id;
    }

    @JsonProperty
    public String type() {
        return type;
    }

    @JsonProperty
    public FunctionResponse function() {
        return function;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ToolCallResponse) obj;
        return Objects.equals(
                this.index,
                that.index
        ) && Objects.equals(
                this.id,
                that.id
        ) && Objects.equals(
                this.type,
                that.type
        ) && Objects.equals(
                this.function,
                that.function
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                index,
                id,
                type,
                function
        );
    }

    @Override
    public String toString() {
        return "ToolCallResponse[" + "index=" + index + ", " + "id=" + id + ", "
                + "type=" + type + ", " + "function=" + function + ']';
    }

}
