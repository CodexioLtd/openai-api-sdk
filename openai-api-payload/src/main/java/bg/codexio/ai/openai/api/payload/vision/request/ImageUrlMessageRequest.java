package bg.codexio.ai.openai.api.payload.vision.request;

import java.util.Objects;

public final class ImageUrlMessageRequest
        implements VisionMessage {
    private final ImageUrlRequest imageUrl;

    public ImageUrlMessageRequest(
            ImageUrlRequest imageUrl
    ) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getType() {
        return "image_url";
    }

    public ImageUrlRequest imageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ImageUrlMessageRequest) obj;
        return Objects.equals(this.imageUrl,
                              that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl);
    }

    @Override
    public String toString() {
        return "ImageUrlMessageRequest[" + "imageUrl=" + imageUrl + ']';
    }

}
