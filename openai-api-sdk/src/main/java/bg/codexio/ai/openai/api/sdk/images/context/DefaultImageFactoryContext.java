package bg.codexio.ai.openai.api.sdk.images.context;

import bg.codexio.ai.openai.api.sdk.images.operation.ImageIOOpetationsFactory;
import bg.codexio.ai.openai.api.sdk.images.operation.ImageOperationsFactory;

import java.util.Objects;

public class DefaultImageFactoryContext
        implements ImageFactoryContext {

    private static DefaultImageFactoryContext instance;

    private final ImageOperationsFactory imageOperationsFactory;

    private DefaultImageFactoryContext() {
        this.imageOperationsFactory = new ImageIOOpetationsFactory();
    }

    public static synchronized DefaultImageFactoryContext getInstance() {
        if (Objects.isNull(instance)) {
            return new DefaultImageFactoryContext();
        }

        return instance;
    }

    @Override
    public ImageOperationsFactory getImageOperationsFactory() {
        return this.imageOperationsFactory;
    }
}