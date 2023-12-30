package bg.codexio.ai.openai.api.payload.chat.request;

public interface ChatTool {
    String getType();

    ChatToolChoice asChoice();

    interface ChatToolChoice {
        String type();
    }
}
