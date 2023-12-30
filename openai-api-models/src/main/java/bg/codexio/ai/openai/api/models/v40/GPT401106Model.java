package bg.codexio.ai.openai.api.models.v40;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * GPT-4 Turbo
 * <p>
 * The latest GPT-4 model with improved instruction following, JSON mode,
 * reproducible outputs, parallel function calling, and more.
 * Returns a maximum of 4,096 output tokens.
 * This preview model is not yet suited for production traffic
 * Context Window: 128,000 tokens
 * </p>
 */
public class GPT401106Model
        extends ModelTypeAbstract {
    public GPT401106Model() {
        super("gpt-4-1106-preview");
    }
}
