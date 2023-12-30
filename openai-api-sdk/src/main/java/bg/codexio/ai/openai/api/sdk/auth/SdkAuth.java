package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;

/**
 * This interface provider an instance of {@link ApiCredentials} <br>
 * It can have multiple implementations according to the way the <b>api
 * key</b> is provided to the application <br>
 * As easy for implementation it is, this library provides the following
 * default implementation for immediate usage:
 * <ul>
 *     <li>{@link FromDeveloper}</li>
 *     <li>{@link FromEnvironment}</li>
 *     <li>{@link FromJson}</li>
 * </ul>
 * As this is a {@link FunctionalInterface} it can also be implemented
 * as a lambda (supplier) in the notation of <pre>() -> new ApiCredentials(..
 * .)</pre>
 */
@FunctionalInterface
public interface SdkAuth {
    /**
     * @return {@link ApiCredentials}
     * @throws NotValidAuthenticationMethod if the API Key is not found as it
     *                                      is an integral part of the
     *                                      authentication
     */
    ApiCredentials credentials();
}
