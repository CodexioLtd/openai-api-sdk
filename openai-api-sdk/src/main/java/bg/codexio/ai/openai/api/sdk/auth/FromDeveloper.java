package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;

/**
 * Use this for manually passing the credentials and still
 * conforming the {@link SdkAuth} contract.
 */
public class FromDeveloper
        implements SdkAuth {

    private final ApiCredentials apiCredentials;

    FromDeveloper(ApiCredentials apiCredentials) {
        this.apiCredentials = apiCredentials;
    }

    /**
     * Factory function to by as close as possible
     * to the other {@link SdkAuth} implementations.
     *
     * @param apiCredentials {@link ApiCredentials}
     * @return new instance of the current class with configured credentials
     */
    public static FromDeveloper doPass(ApiCredentials apiCredentials) {
        return new FromDeveloper(apiCredentials);
    }

    /**
     * @return ready for usage instance of {@link ApiCredentials}
     */
    @Override
    public ApiCredentials credentials() {
        return this.apiCredentials;
    }
}
