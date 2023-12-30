package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.io.File;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/audio/createTranslation">Create translation#Request body</a>
 */
public record TranslationRequest(
        File file,
        String model,
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
            String prompt,
            String responseFormat,
            Double temperature
    ) {

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
    }
}
