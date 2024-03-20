package bg.codexio.ai.openai.api.sdk.download.channel;

public class ReadableByteChannelProviderFactory
        implements ChannelProviderFactory {
    @Override
    public ChannelProvider create() {
        return new ReadableByteChannelProvider();
    }
}