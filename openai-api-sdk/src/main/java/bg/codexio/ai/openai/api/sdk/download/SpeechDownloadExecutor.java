package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.voice.response.AudioBinaryResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.download.SpeechDownloadExecutor.Streams.outputStream;

public class SpeechDownloadExecutor {

    private final UniqueFileNameGenerator uniqueFileNameGenerator;


    public SpeechDownloadExecutor() {
        this(new UUIDNameGeneratorFactory().create());
    }

    public SpeechDownloadExecutor(UniqueFileNameGenerator uniqueFileNameGenerator) {
        this.uniqueFileNameGenerator = uniqueFileNameGenerator;
    }

    /**
     * @param targetFolder where to download the file
     * @param result       a result given by the OpenAI API
     * @param mediaType    the extension of the file to be saved
     * @return the downloaded {@link File} with random UUID name.
     * @throws IOException if the target folder does not exist, or it's not
     *                     writable
     */
    public File downloadTo(
            File targetFolder,
            AudioBinaryResponse result,
            String mediaType
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        var targetFile = new File(targetFolder.getAbsolutePath() + "/"
                                          + this.uniqueFileNameGenerator.generateRandomNamePrefix()
                                          + "." + mediaType);

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
