package bg.codexio.ai.openai.api.models.v35;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Currently points to gpt-3.5-turbo-0613.
 * Context Window: 4,096 tokens
 * <p>
 * Will point to gpt-3.5-turbo-1106 starting Dec 11, 2023.
 * </p>
 */
public class GPT35TurboModel
        extends ModelTypeAbstract {
    public GPT35TurboModel() {
        super("gpt-3.5-turbo");
    }
}
