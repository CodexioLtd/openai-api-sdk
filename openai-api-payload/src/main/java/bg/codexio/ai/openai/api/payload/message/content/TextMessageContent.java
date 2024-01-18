package bg.codexio.ai.openai.api.payload.message.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TextMessageContent
        extends MessageContentAbstract {

    private final TextContent text;

    @JsonCreator
    public TextMessageContent(@JsonProperty("text") TextContent text) {
        super("text");
        this.text = text;
    }

    public TextContent getText() {
        return text;
    }
}