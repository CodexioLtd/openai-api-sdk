package bg.codexio.ai.openai.api.payload.images.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class CreateImageRequest
        implements Streamable,
                   ImageRequest {
    private final String prompt;
    private final String model;
    private final Integer n;
    private final String quality;
    private final String responseFormat;
    private final String size;
    private final String style;
    private final String user;

    public CreateImageRequest(
            String prompt,
            String model,
            Integer n,
            String quality,
            String responseFormat,
            String size,
            String style,
            String user
    ) {
        this.prompt = prompt;
        this.model = model;
        this.n = n;
        this.quality = quality;
        this.responseFormat = responseFormat;
        this.size = size;
        this.style = style;
        this.user = user;
    }

    @Override
    public boolean stream() {
        return false;
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

    @JsonProperty
    public String quality() {
        return quality;
    }

    @Override
    @JsonProperty
    public String responseFormat() {
        return responseFormat;
    }

    @Override
    @JsonProperty
    public String size() {
        return size;
    }

    @JsonProperty
    public String style() {
        return style;
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
        var that = (CreateImageRequest) obj;
        return Objects.equals(
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
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                prompt,
                model,
                n,
                quality,
                responseFormat,
                size,
                style,
                user
        );
    }

    @Override
    public String toString() {
        return "CreateImageRequest[" + "prompt=" + prompt + ", " + "model="
                + model + ", " + "n=" + n + ", " + "quality=" + quality + ", "
                + "responseFormat=" + responseFormat + ", " + "size=" + size
                + ", " + "style=" + style + ", " + "user=" + user + ']';
    }

}
