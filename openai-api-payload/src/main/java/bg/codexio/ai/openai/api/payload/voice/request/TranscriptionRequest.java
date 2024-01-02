package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/audio/createTranscription">Create transcription#Request body</a>
 */
public final class TranscriptionRequest
        implements Streamable {
    private final File file;
    private final String model;
    private final String language;
    private final String prompt;
    private final String responseFormat;
    private final Double temperature;
    private final Boolean stream;

    public TranscriptionRequest() {
        this(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public TranscriptionRequest(
            File file,
            String model,
            String language,
            String prompt,
            String responseFormat,
            Double temperature,
            Boolean stream
    ) {
        this.file = file;
        this.model = model;
        this.language = language;
        this.prompt = prompt;
        this.responseFormat = responseFormat;
        this.temperature = temperature;
        this.stream = stream;
    }

    public static Builder builder() {
        return new Builder(
                null,
                null,
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
    public String language() {
        return language;
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
        var that = (TranscriptionRequest) obj;
        return Objects.equals(
                this.file,
                that.file
        ) && Objects.equals(
                this.model,
                that.model
        ) && Objects.equals(
                this.language,
                that.language
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
                language,
                prompt,
                responseFormat,
                temperature
        );
    }

    @Override
    public String toString() {
        return "TranscriptionRequest[" + "file=" + file + ", " + "model="
                + model + ", " + "language=" + language + ", " + "prompt="
                + prompt + ", " + "responseFormat=" + responseFormat + ", "
                + "temperature=" + temperature + ']';
    }


    public static final class Builder {
        private final File file;
        private final String model;
        private final String language;
        private final String prompt;
        private final String responseFormat;
        private final Double temperature;

        private final Boolean stream;

        public Builder(
                File file,
                String model,
                String language,
                String prompt,
                String responseFormat,
                Double temperature,
                Boolean stream

        ) {
            this.file = file;
            this.model = model;
            this.language = language;
            this.prompt = prompt;
            this.responseFormat = responseFormat;
            this.temperature = temperature;
            this.stream = stream;
        }

        public Builder withFile(File file) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        public Builder withModel(String model) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        public Builder withLanguage(String language) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        public Builder withPrompt(String prompt) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }


        public Builder withFormat(String responseFormat) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        public Builder withTemperature(Double temperature) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        public TranscriptionRequest build() {
            return new TranscriptionRequest(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        public File file() {
            return file;
        }

        public String model() {
            return model;
        }

        public String language() {
            return language;
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

        public Boolean stream() {
            return stream;
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
                    this.language,
                    that.language
            ) && Objects.equals(
                    this.prompt,
                    that.prompt
            ) && Objects.equals(
                    this.responseFormat,
                    that.responseFormat
            ) && Objects.equals(
                    this.temperature,
                    that.temperature
            ) && Objects.equals(
                    this.stream,
                    that.stream
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature,
                    stream
            );
        }

        @Override
        public String toString() {
            return "Builder[" + "file=" + file + ", " + "model=" + model + ", "
                    + "language=" + language + ", " + "prompt=" + prompt + ", "
                    + "responseFormat=" + responseFormat + ", " + "temperature="
                    + temperature + "stream="
                    + stream + ']';
        }

    }
}
