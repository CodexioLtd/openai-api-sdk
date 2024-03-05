package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public final class FileUploadSimplified {

    private FileUploadSimplified() {
    }

    public static String simply(File file) {
        return Files.defaults()
                    .and()
                    .upload()
                    .forAssistants()
                    .immediate()
                    .feed(file);
    }

    public static FileResponse simplyRaw(File file) {
        return Files.defaults()
                    .and()
                    .upload()
                    .forAssistants()
                    .immediate()
                    .feedRaw(file);
    }
}