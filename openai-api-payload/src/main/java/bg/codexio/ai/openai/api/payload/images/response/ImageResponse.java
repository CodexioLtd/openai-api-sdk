package bg.codexio.ai.openai.api.payload.images.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Objects;

public record ImageResponse(
        String b64Json,
        String url,
        String revisedPrompt
)
        implements Mergeable<ImageResponse> {
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
}
