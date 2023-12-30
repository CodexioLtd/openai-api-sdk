package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.vision.DetailedAnalyze;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

/**
 * A simplified stage where some things
 * such as AI Model Type and tokens
 * are already configured.
 */
public class SimplifiedStage {
    private final ApiCredentials credentials;

    SimplifiedStage(SdkAuth auth) {
        this.credentials = auth.credentials();
    }

    /**
     * @return {@link SynchronousPromptStage}
     */
    public SynchronousPromptStage explain(File image) {
        return this.ensureImageChooserStage()
                   .explain(image)
                   .analyze(DetailedAnalyze.HIGH)
                   .andRespond()
                   .immediate();
    }

    /**
     * @return {@link SynchronousPromptStage}
     */
    public SynchronousPromptStage explain(String urlOrBase64) {
        return this.ensureImageChooserStage()
                   .explain(urlOrBase64)
                   .analyze(DetailedAnalyze.HIGH)
                   .andRespond()
                   .immediate();
    }

    private ImageChooserStage ensureImageChooserStage() {
        return Vision.authenticate(new HttpExecutorContext(credentials))
                     .understanding(new ObjectMapper())
                     .poweredByGpt40Vision()
                     .limitResponseTo(300);
    }
}
