package bg.codexio.ai.openai.api.models.whisper;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Speech to text model
 * Currently points to whisper-1.
 */
public class Whisper10
        extends ModelTypeAbstract {
    public Whisper10() {
        super("whisper-1");
    }
}
