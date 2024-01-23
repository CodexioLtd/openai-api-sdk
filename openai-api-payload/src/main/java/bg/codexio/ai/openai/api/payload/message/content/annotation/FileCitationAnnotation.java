package bg.codexio.ai.openai.api.payload.message.content.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;

public class FileCitationAnnotation
        extends AnnotationAbstract {

    private final FileCitation fileCitation;

    @JsonCreator
    public FileCitationAnnotation(
            String text,
            Integer startIndex,
            Integer endIndex,
            FileCitation fileCitation
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