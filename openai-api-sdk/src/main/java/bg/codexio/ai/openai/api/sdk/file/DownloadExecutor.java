package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.file.DownloadExecutor.Streams.outputStream;

public class DownloadExecutor {
    private DownloadExecutor() {
    }

    public static File downloadTo(
            File targetFolder,
            FileContentResponse result,
            String fileName
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        var targetFile = new File(targetFolder.getAbsolutePath()
                                              .concat("/".concat(fileName)));

        try (var os = outputStream(targetFile)) {
            os.write(result.bytes());
        }

        return targetFile;
    }

    static final class Streams {

        private Streams() {

        }

        static FileOutputStream outputStream(File file)
                throws FileNotFoundException {
            return new FileOutputStream(file);
        }
    }
}