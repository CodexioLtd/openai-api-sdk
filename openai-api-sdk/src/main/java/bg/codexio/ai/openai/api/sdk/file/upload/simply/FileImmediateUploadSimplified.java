package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.io.File;

public final class FileImmediateUploadSimplified {

    private FileImmediateUploadSimplified() {
    }

    public static FileResponse simplyRaw(File file) {
        return FileUploadSimplified.toRuntimeSelection()
                                   .immediate()
                                   .feedRaw(file);
    }

    public static String simply(File file) {
        return simplyRaw(file).id();
    }
}