package bg.codexio.ai.openai.api.payload.chat;

import bg.codexio.ai.openai.api.payload.chat.request.ChatTool;

import java.util.Map;

/**
 * Representation of a function as per
 * <a href="https://platform.openai.com/docs/assistants/tools/defining-functions">Defining functions</a>
 */
public class ChatFunction {
    private final String description;
    private final String name;
    private final Map<String, Object> parameters;
    private final ChatTool.ChatToolChoice choice;

    public ChatFunction(
            String description,
            String name,
            Map<String, Object> parameters,
            ChatTool.ChatToolChoice choice
    ) {
        this.description = description;
        this.name = name;
        this.parameters = parameters;
        this.choice = choice;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public ChatTool.ChatToolChoice asChoice() {
        return this.choice;
    }
}
