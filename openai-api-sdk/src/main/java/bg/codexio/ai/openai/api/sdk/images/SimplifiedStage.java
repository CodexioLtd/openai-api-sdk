package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.chat.ChatRuntimeSelectionStage;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simplified stage where some things
 * such as AI Model Type and creativity
 * are already configured.
 */
public class SimplifiedStage<R extends ImageRequest,
        E extends RuntimeSelectionStage> {

    private final ApiCredentials credentials;

    SimplifiedStage(SdkAuth auth) {
        this.credentials = auth.credentials();
    }

    /**
     * Go ahead to specify instructions for image creation with
     * <b>high creativity</b> and <b>dall-e-3</b> model executed with a
     * synchronous call.
     *
     * @return {@link ChatRuntimeSelectionStage}
     */
    public String generate(String instruction) {
        return executeGeneration(instruction);
    }

    protected String executeGeneration(String instruction) {
        return this.ensureCreatorTerminalStage()
                   .andRespond()
                   .immediate()
                   .generate(instruction)
                   .andGetRaw()
                   .data()
                   .get(0)
                   .url();
    }

    protected ImagesTerminalStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> ensureCreatorTerminalStage() {
        HttpExecutorContext context = new HttpExecutorContext(credentials);
        ObjectMapper mapper = new ObjectMapper();

        return Images.<CreateImageRequest>authenticate(context)
                     .understanding(mapper)
                     .creating()
                     .poweredByDallE3()
                     .vivid()
                     .highDefinitioned()
                     .landscape()
                     .expectUrl();
    }
}
