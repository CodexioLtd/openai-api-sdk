package bg.codexio.ai.openai.api.payload.chat.request;

import bg.codexio.ai.openai.api.payload.chat.ChatFunction;

public record FunctionTool(
        ChatFunction function
)
        implements ChatTool {

    @Override
    public String getType() {
        return "function";
    }

    @Override
    public ChatToolChoice asChoice() {
        return null;
    }

    public record FunctionToolChoice(FunctionChoice function)
            implements ChatToolChoice {
        @Override
        public String type() {
            return "function";
        }
    }

}
