package bg.codexio.ai.openai.api.payload.message.content.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilePathAnnotation
        extends AnnotationAbstract {

    private final FilePath filePath;

    @JsonCreator
    public FilePathAnnotation(
            @JsonProperty("text") String text,
            @JsonProperty("start_index") Integer startIndex,
            @JsonProperty("end_index") Integer endIndex,
            @JsonProperty("file_path") FilePath filePath
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