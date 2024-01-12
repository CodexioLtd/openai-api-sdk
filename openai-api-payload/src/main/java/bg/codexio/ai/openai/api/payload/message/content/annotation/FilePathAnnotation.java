package bg.codexio.ai.openai.api.payload.message.content.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;

public class FilePathAnnotation
        extends AnnotationAbstract {

    private final FilePath filePath;

    @JsonCreator
    public FilePathAnnotation(
            String text,
            Integer startIndex,
            Integer endIndex,
            FilePath filePath
    ) {
        super(
                "file_path",
                text,
                startIndex,
                endIndex
        );
        this.filePath = filePath;
    }

    public FilePath getFilePath() {
        return filePath;
    }
}