package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.io.File;
import java.util.function.Consumer;

public final class FileAsyncUploadSimplified {

    private FileAsyncUploadSimplified() {
    }

    public static void simplyRaw(
            File file,
            Consumer<FileResponse> consumer
    ) {
        FileUploadSimplified.toRuntimeSelection()
                            .async()
                            .feed(file)
                            .then(consumer);
    }

    public static void simply(
            File file,
            Consumer<String> consumer
    ) {
        simplyRaw(
                file,
                fileResponse -> consumer.accept(fileResponse.id())
        );
    }
}
