package bg.codexio.ai.openai.api.payload.images.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ImageResponse
        implements Mergeable<ImageResponse> {
    private final String b64Json;
    private final String url;
    private final String revisedPrompt;

    public ImageResponse() {
        this(
                null,
                null,
                null
        );
    }

    public ImageResponse(
            String b64Json,
            String url,
            String revisedPrompt
    ) {
        this.b64Json = b64Json;
        this.url = url;
        this.revisedPrompt = revisedPrompt;
    }

    public static ImageResponse empty() {
        return new ImageResponse(
                null,
                null,
                null
        );
    }

    @Override
    public ImageResponse merge(ImageResponse other) {
        return new ImageResponse(
                Objects.requireNonNullElse(
                        this.b64Json(),
                        other.b64Json()
                ),
                Objects.requireNonNullElse(
                        this.url(),
                        other.url()
                ),
                Objects.requireNonNullElse(
                        this.revisedPrompt(),
                        other.revisedPrompt()
                )
        );
    }

    @JsonProperty
    public String b64Json() {
        return b64Json;
    }

    @JsonProperty
    public String url() {
        return url;
    }

    @JsonProperty
    public String revisedPrompt() {
        return revisedPrompt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ImageResponse) obj;
        return Objects.equals(
                this.b64Json,
                that.b64Json
        ) && Objects.equals(
                this.url,
                that.url
        ) && Objects.equals(
                this.revisedPrompt,
                that.revisedPrompt
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                b64Json,
                url,
                revisedPrompt
        );
    }

    @Override
    public String toString() {
        return "ImageResponse[" + "b64Json=" + b64Json + ", " + "url=" + url
                + ", " + "revisedPrompt=" + revisedPrompt + ']';
    }

}
