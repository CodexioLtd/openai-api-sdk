package bg.codexio.ai.openai.api.models.v40;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;

/**
 * Currently points to gpt-4-vision-preview
 * A 1024 x 1024 square image in detail: high mode costs 765 tokens
 */
public class GPT40VisionPreviewModel
        extends ModelTypeAbstract {
    public GPT40VisionPreviewModel() {
        super("gpt-4-vision-preview");
    }
}
