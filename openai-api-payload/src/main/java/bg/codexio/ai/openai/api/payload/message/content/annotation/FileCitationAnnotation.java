package bg.codexio.ai.openai.api.payload.message.content.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileCitationAnnotation
        extends AnnotationAbstract {

    private final FileCitation fileCitation;

    @JsonCreator
    public FileCitationAnnotation(
            @JsonProperty("text") String text,
            @JsonProperty("start_index") Integer startIndex,
            @JsonProperty("end_index") Integer endIndex,
            @JsonProperty("file_citation") FileCitation fileCitation
    ) {
        super(
                "file_citation",
                text,
                startIndex,
                endIndex
        );
        this.fileCitation = fileCitation;
    }

    public FileCitation getFileCitation() {
        return fileCitation;
    }
}