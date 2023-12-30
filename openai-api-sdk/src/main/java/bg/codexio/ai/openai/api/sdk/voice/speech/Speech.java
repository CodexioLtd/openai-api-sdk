package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.HttpTimeouts;
import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.tts.TTS1HDModel;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
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
 * <a href="https://platform.openai.com/docs/api-reference/audio/createSpeech">Speech API</a>.<br/>
 * The interaction starts by configuring the HTTP connection <br/>
 * either by providing a preconfigured {@link SpeechHttpExecutor} http
 * client, <br/>
 * {@link HttpExecutorContext}, {@link SdkAuth} or leave it to
 * {@link #defaults()}. <br/>
 * Further configuration happens on multiple bypassable stages, whatever is
 * important for specific needs. <br>
 */
public final class Speech {

    private Speech() {
    }

    /**
     * @param executor Configured {@link SpeechHttpExecutor}
     *                 with manually set {@link HttpExecutorContext},
     *                 {@link OkHttpClient} and/or {@link ObjectMapper}.
     * @return {@link AIModelStage} to configure the necessary {@link ModelType}
     */
    public static AIModelStage throughHttp(SpeechHttpExecutor executor) {
        return new AIModelStage(
                executor,
                SpeechRequest.builder()
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
                        new SpeechHttpExecutor(
                                ctx,
                                mapper
                        ),
                        SpeechRequest.builder()
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
     * preconfigures simple {@link TTS1HDModel} for prompting.
     *
     * @param auth of type {@link SdkAuth} <br>
     *             Allows you to choose from one of the default
     *             authentication methods <br>
     *             Options: {@link FromDeveloper}, {@link FromJson},
     *             {@link FromEnvironment} <br>
     * @return {@link SynchronousPromptStage}
     */
    public static SynchronousPromptStage simply(SdkAuth auth) {
        return new SimplifiedStage(auth.credentials()).andRespond()
                                                      .immediate();
    }

    /**
     * Authenticates against OpenAPI with the first success from
     * {@link FromEnvironment} and {@link FromJson} <br/>
     * authentication methods. Preconfigures simple {@link TTS1HDModel} for
     * prompting.
     *
     * @return {@link SynchronousPromptStage}
     */
    public static SynchronousPromptStage simply() {
        return autoAuthenticate(Speech::simply);
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
        return autoAuthenticate(Speech::authenticate);
    }

}
