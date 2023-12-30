package bg.codexio.ai.openai.api.payload.images.request;

import java.io.File;
import java.util.function.Function;

public record ImageRequestBuilder<R extends ImageRequest>(
        Function<ImageRequestBuilder<R>, R> specificRequestCreator,
        String prompt,
        String model,
        Integer n,
        String quality,
        String responseFormat,
        String size,
        String style,
        String user,
        File image,
        File mask
) {
    public static <R extends ImageRequest> ImageRequestBuilder<R> builder() {
        return new ImageRequestBuilder<>(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public ImageRequestBuilder<R> withSpecificRequestCreator(Function<ImageRequestBuilder<R>, R> specificRequestCreator) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withPrompt(String prompt) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withModel(String model) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withN(Integer n) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withQuality(String quality) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withResponseFormat(String responseFormat) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withSize(String size) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withStyle(String style) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withUser(String user) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withImage(File image) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> withMask(File mask) {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }

    public ImageRequestBuilder<R> build() {
        return new ImageRequestBuilder<>(
                specificRequestCreator,
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user,
                image,
                mask
        );
    }
}
