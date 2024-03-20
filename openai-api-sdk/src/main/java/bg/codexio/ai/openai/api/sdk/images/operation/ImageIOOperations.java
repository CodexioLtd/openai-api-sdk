package bg.codexio.ai.openai.api.sdk.images.operation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageIOOperations
        implements ImageOperations {

    @Override
    public BufferedImage read(File file) {
        try {
            return ImageIO.read(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(
            File file,
            BufferedImage image,
            String format
    ) {
        try {
            ImageIO.write(
                    image,
                    format,
                    file
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
