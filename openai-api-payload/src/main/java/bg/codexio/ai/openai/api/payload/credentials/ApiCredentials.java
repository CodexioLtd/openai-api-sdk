package bg.codexio.ai.openai.api.payload.credentials;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * This configuration is used to define the credentials used for auth in the
 * OpenAI API
 *
 * @param apiKey       generated in
 *                     <a href="https://platform.openai.com/api-keys">OpenAI user settings page</a><br>
 * @param organization optional organization, default organization can be
 *                     configured in
 *                     <a href="https://platform.openai.com/api-keys">OpenAI user settings page</a><br>
 * @param baseUrl      optional base url for the API calls, default one is
 *                     <a href="https://api.openai.com/v1">https://api.openai.com/v1</a>
 */
public record ApiCredentials(
        String apiKey,
        String organization,
        String baseUrl
) {

    public static final String BASE_URL = "https://api.openai.com/v1";

    /**
     * Generates credential object with supplied apiKey,
     * <a href="https://api.openai.com/v1">default base url</a>
     * and empty organization
     *
     * @param apiKey generated in
     *               <a href="https://platform.openai.com/api-keys">OpenAI user settings page</a>
     */
    public ApiCredentials(String apiKey) {
        this(
                apiKey,
                "",
                BASE_URL
        );
    }

    @Override
    public String baseUrl() {
        return Optional.ofNullable(this.baseUrl)
                       .map(String::trim)
                       .filter(Predicate.not(String::isBlank))
                       .orElse(BASE_URL);
    }
}
