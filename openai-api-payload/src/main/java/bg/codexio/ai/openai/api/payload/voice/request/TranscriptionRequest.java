package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.io.File;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/audio/createTranscription">Create transcription#Request body</a>
 */
public record TranscriptionRequest(
        File file,
        String model,
        String language,
        String prompt,
        String responseFormat,
        Double temperature
)
        implements Streamable {

    public static Builder builder() {
        return new Builder(
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

    public record Builder(
            File file,
            String model,
            String language,
            String prompt,
            String responseFormat,
            Double temperature
    ) {
        public Builder withFile(File file) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withModel(String model) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withLanguage(String language) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withPrompt(String prompt) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }


        public Builder withFormat(String responseFormat) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public Builder withTemperature(Double temperature) {
            return new Builder(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }

        public TranscriptionRequest build() {
            return new TranscriptionRequest(
                    file,
                    model,
                    language,
                    prompt,
                    responseFormat,
                    temperature
            );
        }
    }
}
