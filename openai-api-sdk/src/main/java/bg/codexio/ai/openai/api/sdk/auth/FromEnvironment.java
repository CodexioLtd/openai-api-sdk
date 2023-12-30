package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;

import static bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables.*;

/**
 * When instantiated and {@link #credentials()} is called, series of
 * environment variables will be scanned to provide necessary
 * {@link ApiCredentials}.
 * The expected environment variables names can be checked in
 * {@link AvailableEnvironmentVariables}
 */
public class FromEnvironment
        implements SdkAuth {
    public static final FromEnvironment AUTH = new FromEnvironment();

    FromEnvironment() {

    }

    /**
     * In this implementation we first look for the <b>api key</b> in the
     * <b>environment variables</b><br>
     * If this variable is missing, a {@link NotValidAuthenticationMethod}
     * error is thrown, as the authentication can never proceed without an
     * <b>api key</b>.<br>
     * If <b>organization id</b> is configured, it is applied, otherwise
     * <b>the default empty value is used</b><br>
     *
     * @return ready for usage instance of {@link ApiCredentials}
     */
    @Override
    public ApiCredentials credentials() {
        var apiKey = this.getEnv(OPENAI_API_KEY.name());
        if (apiKey == null) {
            throw new NotValidAuthenticationMethod(
                    "Environment variable " + "OPENAI_API_KEY "
                            + "not present.");
        }

        var organizationId = this.getEnv(OPENAI_ORG_ID.name());
        if (organizationId == null) {
            organizationId = "";
        }

        return new ApiCredentials(
                apiKey,
                organizationId,
                this.getEnv(OPENAI_API_BASE_URL.name())
        );
    }

    protected String getEnv(String key) {
        return System.getenv(key);
    }
}
