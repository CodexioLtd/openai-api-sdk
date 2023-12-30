package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public interface ImagesPromptStage<E extends RuntimeExecutor> {

    E generate(String prompt);

}
