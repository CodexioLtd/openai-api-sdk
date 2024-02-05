package bg.codexio.ai.openai.api.payload.message.content.annotation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// find way to load interface implementations dynamically
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(
                        value = FileCitationAnnotation.class,
                        name = "file_citation"
                ), @JsonSubTypes.Type(
                value = FilePathAnnotation.class, name = "file_path"
        )
        }
)
public interface Annotation {
    String type();

    String text();

    Integer startIndex();

    Integer endIndex();
}