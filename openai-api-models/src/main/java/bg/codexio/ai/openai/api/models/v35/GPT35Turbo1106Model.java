package bg.codexio.ai.openai.api.models.v35;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Updated GPT 3.5 TurboNew
 * <p>
 * The latest GPT-3.5 Turbo model with improved instruction following, JSON
 * mode,
 * reproducible outputs, parallel function calling, and more.
 * Returns a maximum of 4,096 output tokens.
 * </p>
 * <p>
 * Context Window: 16,385 tokens
 * </p>
 */
public class GPT35Turbo1106Model
        extends ModelTypeAbstract {
    public GPT35Turbo1106Model() {
        super("gpt-3.5-turbo-1106");
    }
}
