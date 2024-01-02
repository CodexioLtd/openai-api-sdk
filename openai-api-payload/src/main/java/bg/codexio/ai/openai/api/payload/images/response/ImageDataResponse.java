package bg.codexio.ai.openai.api.payload.images.response;

import bg.codexio.ai.openai.api.payload.Mergeable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ImageDataResponse
        implements Mergeable<ImageDataResponse> {
    private final Long created;
    private final List<ImageResponse> data;

    public ImageDataResponse() {
        this(null, null);
    }

    public ImageDataResponse(
            Long created,
            List<ImageResponse> data
    ) {
        this.created = created;
        this.data = data;
    }

    public static ImageDataResponse empty() {
        return new ImageDataResponse(
                null,
                new ArrayList<>()
        );
    }

    @Override
    public ImageDataResponse merge(ImageDataResponse other) {
        return new ImageDataResponse(
                Math.max(
                        Objects.requireNonNullElse(
                                this.created(),
                                0L
                        ),
                        Objects.requireNonNullElse(
                                other.created(),
                                0L
                        )
                ),
                Mergeable.join(
                        this.data(),
                        other.data(),
                        x -> x.url() != null || x.b64Json() != null,
                        x -> x.url() != null
                             ? x.url()
                             : x.b64Json(),
                        ImageResponse::empty,
                        ImageResponse::merge,
                        x -> true
                )
        );
    }

    @JsonProperty
    public Long created() {
        return created;
    }

    @JsonProperty
    public List<ImageResponse> data() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ImageDataResponse) obj;
        return Objects.equals(this.created,
                              that.created) && Objects.equals(this.data,
                                                              that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created,
                            data);
    }

    @Override
    public String toString() {
        return "ImageDataResponse[" + "created=" + created + ", " + "data="
                + data + ']';
    }


}
