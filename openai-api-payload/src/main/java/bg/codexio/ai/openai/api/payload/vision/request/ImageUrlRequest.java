package bg.codexio.ai.openai.api.payload.vision.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Objects;

public final class ImageUrlRequest {
    private final String url;
    private final String detail;

    public ImageUrlRequest() {
        this(
                null,
                null
        );
    }

    public ImageUrlRequest(
            String url,
            String detail
    ) {
        this.url = url;
        this.detail = detail;
    }

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

    @JsonProperty
    public String url() {
        return url;
    }

    @JsonProperty
    public String detail() {
        return detail;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ImageUrlRequest) obj;
        return Objects.equals(
                this.url,
                that.url
        ) && Objects.equals(
                this.detail,
                that.detail
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                url,
                detail
        );
    }

    @Override
    public String toString() {
        return "ImageUrlRequest[" + "url=" + url + ", " + "detail=" + detail
                + ']';
    }

}
