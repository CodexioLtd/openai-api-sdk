package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadingRuntimeSelectionStage;

public final class FileUploadSimplified {

    private FileUploadSimplified() {
    }

    public static FileUploadingRuntimeSelectionStage toRuntimeSelection() {
        return Files.defaults()
                    .and()
                    .upload()
                    .forAssistants();
    }
}