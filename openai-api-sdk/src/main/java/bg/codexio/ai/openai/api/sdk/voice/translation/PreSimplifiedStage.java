package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;

import java.io.File;

/**
 * Sets audio file for translation,
 * before all other autoconfiguration.
 */
public class PreSimplifiedStage {

    private final ApiCredentials credentials;

    public PreSimplifiedStage(ApiCredentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return {@link SimplifiedStage}
     * @see AudioFileStage#translate(File)
     */
    public SynchronousPromptStage translate(File audioFile) {
        return new SimplifiedStage(
                this.credentials,
                audioFile
        ).andRespond()
         .immediate();
    }
}
