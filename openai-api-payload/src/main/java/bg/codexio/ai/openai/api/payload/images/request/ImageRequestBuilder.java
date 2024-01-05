package bg.codexio.ai.openai.api.payload.images.request;

import java.io.File;
import java.util.Objects;
import java.util.function.Function;

public final class ImageRequestBuilder<R extends ImageRequest> {
    private final Function<ImageRequestBuilder<R>, R> specificRequestCreator;
    private final String prompt;
    private final String model;
    private final Integer n;
    private final String quality;
    private final String responseFormat;
    private final String size;
    private final String style;
    private final String user;
    private final File image;
    private final File mask;

    public ImageRequestBuilder(
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
        this.specificRequestCreator = specificRequestCreator;
        this.prompt = prompt;
        this.model = model;
        this.n = n;
        this.quality = quality;
        this.responseFormat = responseFormat;
        this.size = size;
        this.style = style;
        this.user = user;
        this.image = image;
        this.mask = mask;
    }

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

    public Function<ImageRequestBuilder<R>, R> specificRequestCreator() {
        return specificRequestCreator;
    }

    public String prompt() {
        return prompt;
    }

    public String model() {
        return model;
    }

    public Integer n() {
        return n;
    }

    public String quality() {
        return quality;
    }

    public String responseFormat() {
        return responseFormat;
    }

    public String size() {
        return size;
    }

    public String style() {
        return style;
    }

    public String user() {
        return user;
    }

    public File image() {
        return image;
    }

    public File mask() {
        return mask;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ImageRequestBuilder) obj;
        return Objects.equals(
                this.specificRequestCreator,
                that.specificRequestCreator
        ) && Objects.equals(
                this.prompt,
                that.prompt
        ) && Objects.equals(
                this.model,
                that.model
        ) && Objects.equals(
                this.n,
                that.n
        ) && Objects.equals(
                this.quality,
                that.quality
        ) && Objects.equals(
                this.responseFormat,
                that.responseFormat
        ) && Objects.equals(
                this.size,
                that.size
        ) && Objects.equals(
                this.style,
                that.style
        ) && Objects.equals(
                this.user,
                that.user
        ) && Objects.equals(
                this.image,
                that.image
        ) && Objects.equals(
                this.mask,
                that.mask
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
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

    @Override
    public String toString() {
        return "ImageRequestBuilder[" + "specificRequestCreator="
                + specificRequestCreator + ", " + "prompt=" + prompt + ", "
                + "model=" + model + ", " + "n=" + n + ", " + "quality="
                + quality + ", " + "responseFormat=" + responseFormat + ", "
                + "size=" + size + ", " + "style=" + style + ", " + "user="
                + user + ", " + "image=" + image + ", " + "mask=" + mask + ']';
    }

}
