package bg.codexio.ai.openai.api.sdk.download.channel;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ReadableByteChannelProvider
        implements ChannelProvider {
    @Override
    public ReadableByteChannel createChannel(InputStream stream) {
        return Channels.newChannel(stream);
    }
}