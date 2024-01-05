package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/audio/createTranslation">Create translation#Request body</a>
 */
public final class TranslationRequest
        implements Streamable {
    private final File file;
    private final String model;
    private final String prompt;
    private final String responseFormat;
    private final Double temperature;

    public TranslationRequest() {
        this(
                null,
                null,
                null,
                null,
                null
        );
    }

    public TranslationRequest(
            File file,
            String model,
            String prompt,
            String responseFormat,
            Double temperature
    ) {
        this.file = file;
        this.model = model;
        this.prompt = prompt;
        this.responseFormat = responseFormat;
        this.temperature = temperature;
    }

    public static Builder builder() {
        return new Builder(
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public boolean stream() {
        return false;
    }

    @JsonProperty
    public File file() {
        return file;
    }

    @JsonProperty
    public String model() {
        return model;
    }

    @JsonProperty
    public String prompt() {
        return prompt;
    }

    @JsonProperty
    public String responseFormat() {
        return responseFormat;
    }

    @JsonProperty
    public Double temperature() {
        return temperature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (TranslationRequest) obj;
        return Objects.equals(
                this.file,
                that.file
        ) && Objects.equals(
                this.model,
                that.model
        ) && Objects.equals(
                this.prompt,
                that.prompt
        ) && Objects.equals(
                this.responseFormat,
                that.responseFormat
        ) && Objects.equals(
                this.temperature,
                that.temperature
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                file,
                model,
                prompt,
                responseFormat,
                temperature
        );
    }

    @Override
    public String toString() {
        return "TranslationRequest[" + "file=" + file + ", " + "model=" + model
                + ", " + "prompt=" + prompt + ", " + "responseFormat="
                + responseFormat + ", " + "temperature=" + temperature + ']';
    }


    public static final class Builder {
        private final File file;
        private final String model;
        private final String prompt;
        private final String responseFormat;
        private final Double temperature;

        public Builder(
                File file,
                String model,
                String prompt,
                String responseFormat,
                Double temperature
        ) {
            this.file = file;
            this.model = model;
            this.prompt = prompt;
            this.responseFormat = responseFormat;
            this.temperature = temperature;
        }

        public Builder withFile(File file) {
            return new Builder(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withModel(String model) {
            return new Builder(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withPrompt(String prompt) {
            return new Builder(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withFormat(String responseFormat) {
            return new Builder(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withTemperature(Double temperature) {
            return new Builder(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public TranslationRequest build() {
            return new TranslationRequest(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public File file() {
            return file;
        }

        public String model() {
            return model;
        }

        public String prompt() {
            return prompt;
        }

        public String responseFormat() {
            return responseFormat;
        }

        public Double temperature() {
            return temperature;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Builder) obj;
            return Objects.equals(
                    this.file,
                    that.file
            ) && Objects.equals(
                    this.model,
                    that.model
            ) && Objects.equals(
                    this.prompt,
                    that.prompt
            ) && Objects.equals(
                    this.responseFormat,
                    that.responseFormat
            ) && Objects.equals(
                    this.temperature,
                    that.temperature
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    file,
                    model,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        @Override
        public String toString() {
            return "Builder[" + "file=" + file + ", " + "model=" + model + ", "
                    + "prompt=" + prompt + ", " + "responseFormat="
                    + responseFormat + ", " + "temperature=" + temperature
                    + ']';
        }

    }
}
