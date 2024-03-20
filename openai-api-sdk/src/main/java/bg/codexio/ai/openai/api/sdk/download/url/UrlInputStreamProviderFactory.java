package bg.codexio.ai.openai.api.sdk.download.url;

public class UrlInputStreamProviderFactory
        implements UrlStreamProviderFactory {
    @Override
    public UrlStreamProvider create() {
        return new UrlInputStreamProvider();
    }
}
