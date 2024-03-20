package bg.codexio.ai.openai.api.sdk.download.context;

import bg.codexio.ai.openai.api.sdk.download.DownloadExecutorFactory;
import bg.codexio.ai.openai.api.sdk.download.channel.ChannelProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGeneratorFactory;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.url.UrlStreamProviderFactory;

public interface DownloadExecutorFactoryContext {
    UniqueFileNameGeneratorFactory getUniqueFileNameGeneratorFactory();

    FileStreamProviderFactory getFileStreamProviderFactory();

    UrlStreamProviderFactory getUrlStreamProviderFactory();

    ChannelProviderFactory getChannelProviderFactory();

    DownloadExecutorFactory getDownloadExecutorFactory();
}