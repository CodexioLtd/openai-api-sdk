package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.HttpTimeouts;
import bg.codexio.ai.openai.api.http.images.CreateImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.EditImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.ImageVariationHttpExecutor;
import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.FromEnvironment;
import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.chat.ImmediateContextStage;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

/**
 * Entrypoint for Images API, supporting
 * <a href="https://platform.openai.com/docs/api-reference/images/create">Creating</a>,
 * <a href="https://platform.openai.com/docs/api-reference/images/createEdit">Editing/a>,
 * and
 * <a href="https://platform.openai.com/docs/api-reference/images/createVariation">Variating</a>.<br/>
 * The interaction starts by configuring the HTTP connection <br/>
 * either by providing a preconfigured http client -
 * {@link CreateImageHttpExecutor}, {@link EditImageHttpExecutor} and
 * {@link ImageVariationHttpExecutor} respectively,<br/>
 * {@link HttpExecutorContext}, {@link SdkAuth} or leave it to
 * {@link #defaults()}. <br/>
 * Further configuration happens on multiple bypassable stages, whatever is
 * important for specific needs. <br>
 */
public final class Images<R extends ImageRequest> {

    private Images() {
    }

    /**
     * @param createExecutor Configured {@link CreateImageHttpExecutor}
     *                       with manually set {@link HttpExecutorContext},
     *                       {@link OkHttpClient} and/or {@link ObjectMapper}.
     * @return {@link CreatingActionTypeStage} to mark the only possible
     * action in this case - creating
     */
    public static CreatingActionTypeStage throughHttp(
            CreateImageHttpExecutor createExecutor
    ) {
        return new CreatingActionTypeStage(createExecutor);
    }

    /**
     * @param editExecutor Configured {@link EditImageHttpExecutor}
     *                     with manually set {@link HttpExecutorContext},
     *                     {@link OkHttpClient} and/or {@link ObjectMapper}.
     * @return {@link EditingActionTypeStage} to mark the only possible
     * action - editing in this case
     */
    public static EditingActionTypeStage throughHttp(
            EditImageHttpExecutor editExecutor
    ) {
        return new EditingActionTypeStage(editExecutor);
    }

    /**
     * @param variationExecutor Configured {@link ImageVariationHttpExecutor}
     *                          with manually set {@link HttpExecutorContext},
     *                          {@link OkHttpClient} and/or
     *                          {@link ObjectMapper}.
     * @return {@link VariatingActionTypeStage} to to mark the only possible
     * action in this case - variating
     */
    public static VariatingActionTypeStage throughHttp(
            ImageVariationHttpExecutor variationExecutor
    ) {
        return new VariatingActionTypeStage(variationExecutor);
    }

    /**
     * Authenticates against OpenAI API with manually supplied <br/>
     * {@link ApiCredentials} and {@link HttpTimeouts}. Mostly used if
     * credentials <br/>
     * are fetched from external source.
     *
     * @param context {@link HttpExecutorContext}
     * @return {@link HttpBuilder<ActionTypeStage>} so timeouts can be
     * reconfigured and possibly {@link ObjectMapper} is supplied.
     */
    public static <R extends ImageRequest> HttpBuilder<ActionTypeStage<R>> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (ctx, mapper) -> new ActionTypeStage<R>(
                        new CreateImageHttpExecutor(
                                ctx,
                                mapper
                        ),
                        new EditImageHttpExecutor(
                                ctx,
                                mapper
                        ),
                        new ImageVariationHttpExecutor(
                                ctx,
                                mapper
                        )
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
     * @return {@link HttpBuilder<ActionTypeStage>} so timeouts can be
     * reconfigured and possibly {@link ObjectMapper} is supplied.
     */
    public static <R extends ImageRequest> HttpBuilder<ActionTypeStage<R>> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    /**
     * Authenticates against OpenAI API with an implementation of
     * {@link SdkAuth} and </br>
     * preconfigures simple {@link DallE30} powered instance for creating
     * images.
     *
     * @param auth of type {@link SdkAuth} <br>
     *             Allows you to choose from one of the default
     *             authentication methods <br>
     *             Options: {@link FromDeveloper}, {@link FromJson},
     *             {@link FromEnvironment} <br>
     * @return {@link ImmediateContextStage}
     */
    public static <R extends ImageRequest> SimplifiedStage<R,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> simply(SdkAuth auth) {
        return new SimplifiedStage<>(auth);
    }

    /**
     * Authenticates against OpenAPI with the first success from
     * {@link FromEnvironment} and {@link FromJson} <br/>
     * authentication methods. Preconfigures simple {@link DallE30} powered
     * instance for creating images.
     *
     * @return {@link ImmediateContextStage}
     */
    public static <R extends ImageRequest> SimplifiedStage<CreateImageRequest
            , PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> simply() {
        return autoAuthenticate(Images::simply);
    }

    /**
     * Authenticates against OpenAPI with the first success from
     * {@link FromEnvironment} and {@link FromJson} <br/>
     * authentication methods. All other configurations can be manually
     * customized <br/>
     *
     * @return {@link HttpBuilder<ActionTypeStage>} so timeouts can be
     * reconfigured and possibly {@link ObjectMapper} is supplied.
     */
    public static <R extends ImageRequest> HttpBuilder<ActionTypeStage<R>> defaults() {
        return autoAuthenticate(Images::authenticate);
    }

}
