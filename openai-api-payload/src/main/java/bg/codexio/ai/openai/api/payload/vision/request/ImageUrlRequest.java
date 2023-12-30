package bg.codexio.ai.openai.api.payload.vision.request;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public record ImageUrlRequest(
        String url,
        String detail
) {

    public static ImageUrlRequest fromLocalFile(File image) {
        try {
            var mimeType = Files.probeContentType(image.toPath());
            if (mimeType == null) {
                mimeType = "image/png";
            }

            var base64 = Base64.getEncoder()
                               .encodeToString(Files.readAllBytes(image.toPath()));

            return new ImageUrlRequest(
                    "data:" + mimeType + ";base64," + base64,
                    null
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ImageUrlRequest withUrlOrBase64(String urlOrBase64) {
        return new ImageUrlRequest(
                urlOrBase64,
                this.detail
        );
    }

    public ImageUrlRequest withFile(File file) {
        return new ImageUrlRequest(
                fromLocalFile(file).url(),
                this.detail
        );
    }

    public ImageUrlRequest withDetail(String detail) {
        return new ImageUrlRequest(
                this.url(),
                detail
        );
    }
}
