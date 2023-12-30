package bg.codexio.ai.openai.api.models.tts;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Text to speech model
 * Currently points to tts-1.
 */
public class TTS1Model
        extends ModelTypeAbstract {
    public TTS1Model() {
        super("tts-1");
    }
}
