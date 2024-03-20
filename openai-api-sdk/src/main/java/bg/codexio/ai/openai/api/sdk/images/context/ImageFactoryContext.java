package bg.codexio.ai.openai.api.sdk.images.context;

import bg.codexio.ai.openai.api.sdk.images.operation.ImageOperationsFactory;

public interface ImageFactoryContext {
    ImageOperationsFactory getImageOperationsFactory();
}
