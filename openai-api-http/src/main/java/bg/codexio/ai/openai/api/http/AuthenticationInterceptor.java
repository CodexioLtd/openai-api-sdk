package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * {@link okhttp3.OkHttp} interceptor type for OpenAI authentication
 */
public class AuthenticationInterceptor
        implements Interceptor {

    private final HttpExecutorContext context;

    /**
     * @param context passed along the HTTP configuration, mainly the
     *                credentials
     */
    public AuthenticationInterceptor(HttpExecutorContext context) {
        this.context = context;
    }

    /**
     * Sends the {@link ApiCredentials#apiKey()} as a Bearer token.
     * In addition, sets OpenAI-Organization if
     * {@link ApiCredentials#organization()} is present.
     */
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        return chain.proceed(chain.request()
                                  .newBuilder()
                                  .addHeader(
                                          "Content-Type",
                                          "application/json"
                                  )
                                  .addHeader(
                                          "Authorization",
                                          "Bearer " + this.context.credentials()
                                                                  .apiKey()
                                  )
                                  .addHeader(
                                          this.context.credentials()
                                                      .organization()
                                                      .isBlank()
                                          ? "X-Ignore"
                                          : "OpenAI-Organization",
                                          this.context.credentials()
                                                      .organization()
                                  )
                                  .build());
    }
}
