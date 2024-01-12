package bg.codexio.ai.openai.api.payload.message.content;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TextMessageContent
        extends MessageContentAbstract {

    private final TextContent textContent;

    @JsonCreator
    public TextMessageContent(TextContent textContent) {
        super("text");
        this.textContent = textContent;
    }

    public TextContent getTextContent() {
        return textContent;
    }
}