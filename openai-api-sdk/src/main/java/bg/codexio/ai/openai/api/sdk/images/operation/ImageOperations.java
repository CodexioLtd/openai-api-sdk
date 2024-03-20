package bg.codexio.ai.openai.api.sdk.images.operation;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageOperations {
    BufferedImage read(File file);

    void write(
            File file,
            BufferedImage image,
            String format
    );
}
