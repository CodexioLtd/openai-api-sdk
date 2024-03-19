package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.payload.images.response.ImageResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.Channels;
import java.util.Base64;

import static bg.codexio.ai.openai.api.sdk.download.ImageDownloadExecutor.Streams.outputStream;

public class ImageDownloadExecutor {

    private final UniqueFileNameGenerator uniqueFileNameGenerator;

    public ImageDownloadExecutor() {
        this(new UUIDNameGeneratorFactory().create());
    }

    public ImageDownloadExecutor(UniqueFileNameGenerator uniqueFileNameGenerator) {
        this.uniqueFileNameGenerator = uniqueFileNameGenerator;
    }

    public File[] downloadTo(
            File targetFolder,
            ImageDataResponse response,
            Format format
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        for (var image : response.data()) {
            downloadFile(
                    targetFolder,
                    format,
                    image
            );
        }

        return targetFolder.listFiles();
    }


    public File downloadFile(
            File targetFolder,
            Format format,
            ImageResponse image
    ) throws IOException {
        var fileName = targetFolder.getAbsoluteFile() + "/"
                + this.uniqueFileNameGenerator.generateRandomNamePrefix()
                + ".png";
        try (var fos = outputStream(fileName)) {
            if (format == Format.URL) {
                var channel = Channels.newChannel(URI.create(image.url())
                                                     .toURL()
                                                     .openStream());
                fos.getChannel()
                   .transferFrom(
                           channel,
                           0,
                           Long.MAX_VALUE
                   );
            } else {
                fos.write(Base64.getDecoder()
                                .decode(image.b64Json()));
            }

            return new File(fileName);
        }
    }

    protected static final class Streams {

        private Streams() {
        }

        static FileOutputStream outputStream(String fileName)
                throws FileNotFoundException {
            return new FileOutputStream(fileName);
        }
    }

}
