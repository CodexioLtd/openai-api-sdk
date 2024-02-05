package bg.codexio.ai.openai.api.payload.message.content;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// find way to load interface implementations dynamically
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(
                        value = TextMessageContent.class, name = "text"
                ), @JsonSubTypes.Type(
                value = ImageFileContent.class, name = "image_file"
        )
        }
)
public interface MessageContent {
    String type();
}