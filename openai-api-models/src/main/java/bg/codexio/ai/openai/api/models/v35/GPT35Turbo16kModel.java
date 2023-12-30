package bg.codexio.ai.openai.api.models.v35;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Currently points to gpt-3.5-turbo-0613.
 * Context Window: 16,385 tokens
 * <p>
 * Will point to gpt-3.5-turbo-1106 starting Dec 11, 2023. See
 * </p>
 */
public class GPT35Turbo16kModel
        extends ModelTypeAbstract {
    public GPT35Turbo16kModel() {
        super("gpt-3.5-turbo-16k");
    }
}
