package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.Streamable;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/audio/createSpeech">Create speech#Request body</a>
 */
public record SpeechRequest(
        String model,
        String input,
        String voice,
        String responseFormat,
        Double speed
)
        implements Streamable {

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

    public record Builder(
            String model,
            String input,
            String voice,
            String responseFormat,
            Double speed
    ) {

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
    }
}
