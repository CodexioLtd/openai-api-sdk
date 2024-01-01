package bg.codexio.ai.openai.api.payload.chat.request;

import bg.codexio.ai.openai.api.payload.chat.ChatFunction;

import java.util.Objects;

public final class FunctionTool
        implements ChatTool {
    private final ChatFunction function;

    public FunctionTool(
            ChatFunction function
    ) {
        this.function = function;
    }

    @Override
    public String getType() {
        return "function";
    }

    @Override
    public ChatToolChoice asChoice() {
        return null;
    }

    public ChatFunction function() {
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
        var that = (FunctionTool) obj;
        return Objects.equals(this.function,
                              that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function);
    }

    @Override
    public String toString() {
        return "FunctionTool[" + "function=" + function + ']';
    }


    public static final class FunctionToolChoice implements ChatToolChoice {
        private final FunctionChoice function;

        public FunctionToolChoice(FunctionChoice function) {
            this.function = function;
        }

        public FunctionChoice function() {
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
            var that = (FunctionToolChoice) obj;
            return Objects.equals(
                    this.function,
                    that.function
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(function);
        }

        @Override
        public String toString() {
            return "FunctionToolChoice[" + "function=" + function + ']';
        }

        @Override
        public String type() {
            return "function";
        }
    }

}
