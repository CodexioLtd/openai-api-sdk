package bg.codexio.ai.openai.api.sdk.download.context;

import bg.codexio.ai.openai.api.sdk.download.DefaultDownloadExecutorFactory;
import bg.codexio.ai.openai.api.sdk.download.DownloadExecutorFactory;
import bg.codexio.ai.openai.api.sdk.download.channel.ChannelProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.channel.ReadableByteChannelProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.name.UUIDNameGeneratorFactory;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGeneratorFactory;
import bg.codexio.ai.openai.api.sdk.download.stream.FileOutputStreamProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.url.UrlInputStreamProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.url.UrlStreamProviderFactory;

import java.util.Objects;

public class DefaultDownloadExecutorFactoryContext
        implements DownloadExecutorFactoryContext {

    private static DefaultDownloadExecutorFactoryContext instance;

    private final UniqueFileNameGeneratorFactory uniqueFileNameGeneratorFactory;

    private final FileStreamProviderFactory fileStreamProviderFactory;

    private final UrlStreamProviderFactory urlStreamProviderFactory;

    private final ChannelProviderFactory channelProviderFactory;

    private final DownloadExecutorFactory downloadExecutorFactory;


    private DefaultDownloadExecutorFactoryContext() {
        this.uniqueFileNameGeneratorFactory = new UUIDNameGeneratorFactory();
        this.fileStreamProviderFactory = new FileOutputStreamProviderFactory();
        this.urlStreamProviderFactory = new UrlInputStreamProviderFactory();
        this.channelProviderFactory = new ReadableByteChannelProviderFactory();
        this.downloadExecutorFactory = new DefaultDownloadExecutorFactory();
    }

    public static synchronized DefaultDownloadExecutorFactoryContext getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DefaultDownloadExecutorFactoryContext();
        }

        return instance;
    }

    @Override
    public UniqueFileNameGeneratorFactory getUniqueFileNameGeneratorFactory() {
        return this.uniqueFileNameGeneratorFactory;
    }

    @Override
    public FileStreamProviderFactory getFileStreamProviderFactory() {
        return this.fileStreamProviderFactory;
    }

    @Override
    public UrlStreamProviderFactory getUrlStreamProviderFactory() {
        return this.urlStreamProviderFactory;
    }

    @Override
    public ChannelProviderFactory getChannelProviderFactory() {
        return this.channelProviderFactory;
    }

    @Override
    public DownloadExecutorFactory getDownloadExecutorFactory() {
        return this.downloadExecutorFactory;
    }
}
