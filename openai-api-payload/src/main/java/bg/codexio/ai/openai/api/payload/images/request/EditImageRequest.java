package bg.codexio.ai.openai.api.payload.images.request;

import java.io.File;
import java.util.Objects;

public final class EditImageRequest
        implements ImageRequest {
    private final File image;
    private final String prompt;
    private final File mask;
    private final String model;
    private final Integer n;
    private final String size;
    private final String responseFormat;
    private final String user;

    public EditImageRequest(
            File image,
            String prompt,
            File mask,
            String model,
            Integer n,
            String size,
            String responseFormat,
            String user
    ) {
        this.image = image;
        this.prompt = prompt;
        this.mask = mask;
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

    public File image() {
        return image;
    }

    @Override
    public String prompt() {
        return prompt;
    }

    public File mask() {
        return mask;
    }

    @Override
    public String model() {
        return model;
    }

    @Override
    public Integer n() {
        return n;
    }

    @Override
    public String size() {
        return size;
    }

    @Override
    public String responseFormat() {
        return responseFormat;
    }

    @Override
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
        var that = (EditImageRequest) obj;
        return Objects.equals(this.image,
                              that.image) && Objects.equals(this.prompt,
                                                            that.prompt)
                && Objects.equals(this.mask,
                                  that.mask) && Objects.equals(this.model,
                                                               that.model)
                && Objects.equals(this.n,
                                  that.n) && Objects.equals(this.size,
                                                            that.size)
                && Objects.equals(this.responseFormat,
                                  that.responseFormat) && Objects.equals(this.user,
                                                                         that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image,
                            prompt,
                            mask,
                            model,
                            n,
                            size,
                            responseFormat,
                            user);
    }

    @Override
    public String toString() {
        return "EditImageRequest[" + "image=" + image + ", " + "prompt="
                + prompt + ", " + "mask=" + mask + ", " + "model=" + model
                + ", " + "n=" + n + ", " + "size=" + size + ", "
                + "responseFormat=" + responseFormat + ", " + "user=" + user
                + ']';
    }

}
