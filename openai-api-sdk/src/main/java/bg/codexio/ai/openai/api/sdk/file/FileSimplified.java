package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.io.File;

public final class FileSimplified {

    private FileSimplified() {
    }

    public static String simply(File file) {
        return Files.defaults()
                    .and()
                    .upload()
                    .forAssistants()
                    .feed(file);
    }

    public static FileResponse simplyRaw(File file) {
        return Files.defaults()
                    .and()
                    .upload()
                    .forAssistants()
                    .feedRaw(file);
    }
}