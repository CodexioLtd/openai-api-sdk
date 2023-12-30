package bg.codexio.ai.openai.api.models.v40;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Currently points to gpt-4-0613.
 * Context Window: 8,192 tokens
 */
public class GPT40Model
        extends ModelTypeAbstract {
    public GPT40Model() {
        super("gpt-4");
    }
}
