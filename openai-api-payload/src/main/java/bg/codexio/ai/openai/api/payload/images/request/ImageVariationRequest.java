package bg.codexio.ai.openai.api.payload.images.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.Objects;

public final class ImageVariationRequest
        implements ImageRequest,
                   Streamable {
    private final File image;
    private final String prompt;
    private final String model;
    private final Integer n;
    private final String size;
    private final String responseFormat;
    private final String user;

    public ImageVariationRequest(
            File image,
            String prompt,
            String model,
            Integer n,
            String size,
            String responseFormat,
            String user
    ) {
        this.image = image;
        this.prompt = prompt;
        this.model = model;
        this.n = n;
        this.size = size;
        this.responseFormat = responseFormat;
        this.user = user;
    }

    @Override
    public boolean stream() {
        return false;
    }

    @JsonProperty
    public File image() {
        return image;
    }

    @Override
    @JsonProperty
    public String prompt() {
        return prompt;
    }

    @Override
    @JsonProperty
    public String model() {
        return model;
    }

    @Override
    @JsonProperty
    public Integer n() {
        return n;
    }

    @Override
    @JsonProperty
    public String size() {
        return size;
    }

    @Override
    @JsonProperty
    public String responseFormat() {
        return responseFormat;
    }

    @Override
    @JsonProperty
    public String user() {
        return user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ImageVariationRequest) obj;
        return Objects.equals(
                this.image,
                that.image
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
                this.size,
                that.size
        ) && Objects.equals(
                this.responseFormat,
                that.responseFormat
        ) && Objects.equals(
                this.user,
                that.user
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                image,
                prompt,
                model,
                n,
                size,
                responseFormat,
                user
        );
    }

    @Override
    public String toString() {
        return "ImageVariationRequest[" + "image=" + image + ", " + "prompt="
                + prompt + ", " + "model=" + model + ", " + "n=" + n + ", "
                + "size=" + size + ", " + "responseFormat=" + responseFormat
                + ", " + "user=" + user + ']';
    }

}
