package bg.codexio.ai.openai.api.sdk.download.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class UrlInputStreamProvider
        implements UrlStreamProvider {

    @Override
    public InputStream openStream(String url) throws IOException {
        return URI.create(url)
                  .toURL()
                  .openStream();
    }
}
