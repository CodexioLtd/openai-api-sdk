package bg.codexio.ai.openai.api.models.tts;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Text to speech model
 * Currently points to tts-1-hd.
 */
public class TTS1HDModel
        extends ModelTypeAbstract {
    public TTS1HDModel() {
        super("tts-1-hd");
    }
}
