package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.HttpTimeouts;
import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.FromEnvironment;
import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

/**
 * Entrypoint for
 * <a href="https://platform.openai.com/docs/api-reference/audio/createTranslation">Translation API</a>.<br/>
 * The interaction starts by configuring the HTTP connection <br/>
 * either by providing a preconfigured {@link TranslationHttpExecutor} http
 * client, <br/>
 * {@link HttpExecutorContext}, {@link SdkAuth} or leave it to
 * {@link #defaults()}. <br/>
 * Further configuration happens on multiple bypassable stages, whatever is
 * important for specific needs. <br>
 */
public final class Translation {

    private Translation() {
    }

    /**
     * @param executor Configured {@link TranslationHttpExecutor}
     *                 with manually set {@link HttpExecutorContext},
     *                 {@link OkHttpClient} and/or {@link ObjectMapper}.
     * @return {@link AIModelStage} to configure the necessary {@link ModelType}
     */
    public static AIModelStage throughHttp(TranslationHttpExecutor executor) {
        return new AIModelStage(
                executor,
                TranslationRequest.builder()
        );
    }

    /**
     * Authenticates against OpenAI API with manually supplied <br/>
     * {@link ApiCredentials} and {@link HttpTimeouts}. Mostly used if
     * credentials <br/>
     * are fetched from external source.
     *
     * @param context {@link HttpExecutorContext}
     * @return {@link HttpBuilder<AIModelStage>} so timeouts can be
     * reconfigured and possibly {@link ObjectMapper} is supplied.
     */
    public static HttpBuilder<AIModelStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (ctx, mapper) -> new AIModelStage(
                        new TranslationHttpExecutor(
                                ctx,
                                mapper
                        ),
                        TranslationRequest.builder()
                )
        );
    }

    /**
     * Authenticates against OpenAI API with an implementation of
     * {@link SdkAuth}.
     *
     * @param auth of type {@link SdkAuth} <br>
     *             Allows you to choose from one of the default
     *             authentication methods <br>
     *             Options: {@link FromDeveloper}, {@link FromJson},
     *             {@link FromEnvironment} <br>
     * @return {@link HttpBuilder<AIModelStage>} so timeouts can be
     * reconfigured and possibly {@link ObjectMapper} is supplied.
     */
    public static HttpBuilder<AIModelStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    /**
     * Authenticates against OpenAI API with an implementation of
     * {@link SdkAuth} and </br>
     * preconfigures simple {@link Whisper10} for prompting.
     *
     * @param auth of type {@link SdkAuth} <br>
     *             Allows you to choose from one of the default
     *             authentication methods <br>
     *             Options: {@link FromDeveloper}, {@link FromJson},
     *             {@link FromEnvironment} <br>
     * @return {@link PreSimplifiedStage}
     */
    public static PreSimplifiedStage simply(SdkAuth auth) {
        return new PreSimplifiedStage(auth.credentials());
    }

    /**
     * Authenticates against OpenAPI with the first success from
     * {@link FromEnvironment} and {@link FromJson} <br/>
     * authentication methods. Preconfigures simple {@link Whisper10} for
     * prompting.
     *
     * @return {@link PreSimplifiedStage}
     */
    public static PreSimplifiedStage simply() {
        return autoAuthenticate(Translation::simply);
    }

    /**
     * Authenticates against OpenAPI with the first success from
     * {@link FromEnvironment} and {@link FromJson} <br/>
     * authentication methods. All other configurations can be manually
     * customized <br/>
     *
     * @return {@link HttpBuilder<AIModelStage>} so timeouts can be
     * reconfigured and possibly {@link ObjectMapper} is supplied.
     */
    public static HttpBuilder<AIModelStage> defaults() {
        return autoAuthenticate(Translation::authenticate);
    }

}
