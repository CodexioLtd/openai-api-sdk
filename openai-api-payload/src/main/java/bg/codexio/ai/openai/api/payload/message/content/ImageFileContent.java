package bg.codexio.ai.openai.api.payload.message.content;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ImageFileContent
        extends MessageContentAbstract {

    private final String fileId;

    @JsonCreator
    public ImageFileContent(String fileId) {
        super("image_file");
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }
}