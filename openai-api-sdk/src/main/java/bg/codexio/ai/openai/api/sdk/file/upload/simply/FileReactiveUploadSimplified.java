package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import reactor.core.publisher.Mono;

import java.io.File;

public final class FileReactiveUploadSimplified {

    private FileReactiveUploadSimplified() {
    }

    public static Mono<FileResponse> simplyRaw(File file) {
        return FileUploadSimplified.toRuntimeSelection()
                                   .reactive()
                                   .feedRaw(file)
                                   .response();
    }

    public static Mono<String> simply(File file) {
        return simplyRaw(file).map(FileResponse::id);
    }
}