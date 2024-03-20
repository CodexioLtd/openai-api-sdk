package bg.codexio.ai.openai.api.sdk.download.channel;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

public interface ChannelProvider {
    ReadableByteChannel createChannel(InputStream inputStream);
}