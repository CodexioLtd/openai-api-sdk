package bg.codexio.ai.openai.api.models.v40;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Currently points to gpt-4-32k-0613.
 * Context Window: 32,768 tokens
 */
public class GPT4032kModel
        extends ModelTypeAbstract {
    public GPT4032kModel() {
        super("gpt-4-32k");
    }
}
