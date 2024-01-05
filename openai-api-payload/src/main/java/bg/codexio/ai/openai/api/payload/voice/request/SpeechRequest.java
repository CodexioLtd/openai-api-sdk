package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/audio/createSpeech">Create speech#Request body</a>
 */
public final class SpeechRequest
        implements Streamable {
    private final String model;
    private final String input;
    private final String voice;
    private final String responseFormat;
    private final Double speed;

    public SpeechRequest() {
        this(
                null,
                null,
                null,
                null,
                null
        );
    }

    public SpeechRequest(
            String model,
            String input,
            String voice,
            String responseFormat,
            Double speed
    ) {
        this.model = model;
        this.input = input;
        this.voice = voice;
        this.responseFormat = responseFormat;
        this.speed = speed;
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
    public String model() {
        return model;
    }

    @JsonProperty
    public String input() {
        return input;
    }

    @JsonProperty
    public String voice() {
        return voice;
    }

    @JsonProperty
    public String responseFormat() {
        return responseFormat;
    }

    @JsonProperty
    public Double speed() {
        return speed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (SpeechRequest) obj;
        return Objects.equals(
                this.model,
                that.model
        ) && Objects.equals(
                this.input,
                that.input
        ) && Objects.equals(
                this.voice,
                that.voice
        ) && Objects.equals(
                this.responseFormat,
                that.responseFormat
        ) && Objects.equals(
                this.speed,
                that.speed
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                model,
                input,
                voice,
                responseFormat,
                speed
        );
    }

    @Override
    public String toString() {
        return "SpeechRequest[" + "model=" + model + ", " + "input=" + input
                + ", " + "voice=" + voice + ", " + "responseFormat="
                + responseFormat + ", " + "speed=" + speed + ']';
    }


    public static final class Builder {
        private final String model;
        private final String input;
        private final String voice;
        private final String responseFormat;
        private final Double speed;

        public Builder(
                String model,
                String input,
                String voice,
                String responseFormat,
                Double speed
        ) {
            this.model = model;
            this.input = input;
            this.voice = voice;
            this.responseFormat = responseFormat;
            this.speed = speed;
        }

        public Builder withModel(String model) {
            return new Builder(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        public Builder withInput(String input) {
            return new Builder(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        public Builder withVoice(String voice) {
            return new Builder(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        public Builder withFormat(String responseFormat) {
            return new Builder(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        public Builder withSpeed(Double speed) {
            return new Builder(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        public SpeechRequest build() {
            return new SpeechRequest(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        public String model() {
            return model;
        }

        public String input() {
            return input;
        }

        public String voice() {
            return voice;
        }

        public String responseFormat() {
            return responseFormat;
        }

        public Double speed() {
            return speed;
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
                    this.model,
                    that.model
            ) && Objects.equals(
                    this.input,
                    that.input
            ) && Objects.equals(
                    this.voice,
                    that.voice
            ) && Objects.equals(
                    this.responseFormat,
                    that.responseFormat
            ) && Objects.equals(
                    this.speed,
                    that.speed
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    model,
                    input,
                    voice,
                    responseFormat,
                    speed
            );
        }

        @Override
        public String toString() {
            return "Builder[" + "model=" + model + ", " + "input=" + input
                    + ", " + "voice=" + voice + ", " + "responseFormat="
                    + responseFormat + ", " + "speed=" + speed + ']';
        }

    }
}
