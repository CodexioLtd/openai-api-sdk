package bg.codexio.ai.openai.api.sdk.images.operation;

public class ImageIOOpetationsFactory
        implements ImageOperationsFactory {
    @Override
    public ImageOperations create() {
        return new ImageIOOperations();
    }
}