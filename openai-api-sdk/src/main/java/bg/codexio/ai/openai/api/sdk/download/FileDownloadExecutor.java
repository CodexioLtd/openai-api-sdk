package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.download.FileDownloadExecutor.Streams.outputStream;

public class FileDownloadExecutor {

    private final UniqueFileNameGenerator uniqueFileNameGenerator;

    public FileDownloadExecutor() {
        this(new UUIDNameGeneratorFactory().create());
    }

    public FileDownloadExecutor(UniqueFileNameGenerator uniqueFileNameGenerator) {
        this.uniqueFileNameGenerator = uniqueFileNameGenerator;
    }

    public File downloadTo(
            File targetFolder,
            FileContentResponse result,
            String fileName
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        var uniqueFileName =
                this.uniqueFileNameGenerator.generateRandomNamePrefix()
                                                         .concat(fileName);
        var targetFile = new File(targetFolder.getAbsolutePath()
                                              .concat("/".concat(uniqueFileName)));

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