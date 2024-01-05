package bg.codexio.ai.openai.api.payload.credentials;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This configuration is used to define the credentials used for auth in the
 * OpenAI API
 */
public final class ApiCredentials {

    public static final String BASE_URL = "https://api.openai.com/v1";
    private final String apiKey;
    private final String organization;
    private final String baseUrl;

    /**
     * @param apiKey       generated in
     *                     <a href="https://platform.openai.com/api-keys">OpenAI user settings page</a><br>
     * @param organization optional organization, default organization can be
     *                     configured in
     *                     <a href="https://platform.openai.com/api-keys">OpenAI user settings page</a><br>
     * @param baseUrl      optional base url for the API calls, default one is
     *                     <a href="https://api.openai.com/v1">https://api.openai.com/v1</a>
     */
    public ApiCredentials(
            String apiKey,
            String organization,
            String baseUrl
    ) {
        this.apiKey = apiKey;
        this.organization = organization;
        this.baseUrl = baseUrl;
    }

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

    public String baseUrl() {
        return Optional.ofNullable(this.baseUrl)
                       .map(String::trim)
                       .filter(Predicate.not(String::isBlank))
                       .orElse(BASE_URL);
    }

    public String apiKey() {
        return apiKey;
    }

    public String organization() {
        return organization;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ApiCredentials) obj;
        return Objects.equals(
                this.apiKey,
                that.apiKey
        ) && Objects.equals(
                this.organization,
                that.organization
        ) && Objects.equals(
                this.baseUrl,
                that.baseUrl
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                apiKey,
                organization,
                baseUrl
        );
    }

    @Override
    public String toString() {
        return "ApiCredentials[" + "apiKey=" + apiKey + ", " + "organization="
                + organization + ", " + "baseUrl=" + baseUrl + ']';
    }

}
