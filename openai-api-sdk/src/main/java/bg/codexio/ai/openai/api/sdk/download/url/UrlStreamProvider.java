package bg.codexio.ai.openai.api.sdk.download.url;

import java.io.IOException;
import java.io.InputStream;

public interface UrlStreamProvider {
    InputStream openStream(String url) throws IOException;
}