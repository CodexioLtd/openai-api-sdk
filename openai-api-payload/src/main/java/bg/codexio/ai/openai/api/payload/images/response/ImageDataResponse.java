package bg.codexio.ai.openai.api.payload.images.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record ImageDataResponse(
        Long created,
        List<ImageResponse> data
)
        implements Mergeable<ImageDataResponse> {

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

}
