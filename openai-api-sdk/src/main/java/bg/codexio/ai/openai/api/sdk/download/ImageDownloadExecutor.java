package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.FileContentProvider;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.download.channel.ChannelProvider;
import bg.codexio.ai.openai.api.sdk.download.channel.ChannelProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.context.DefaultDownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.context.DownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGenerator;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGeneratorFactory;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProvider;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProviderFactory;
import bg.codexio.ai.openai.api.sdk.download.url.UrlStreamProvider;
import bg.codexio.ai.openai.api.sdk.download.url.UrlStreamProviderFactory;

import java.io.File;
import java.io.IOException;


public class ImageDownloadExecutor
        implements DownloadExecutor {

    private final UniqueFileNameGenerator uniqueFileNameGenerator;

    private final FileStreamProvider fileStreamProvider;

    private final UrlStreamProvider urlStreamProvider;

    private final ChannelProvider channelProvider;

    public ImageDownloadExecutor(
            UniqueFileNameGenerator uniqueFileNameGenerator,
            FileStreamProvider fileStreamProvider,
            UrlStreamProvider urlStreamProvider,
            ChannelProvider channelProvider
    ) {
        this.uniqueFileNameGenerator = uniqueFileNameGenerator;
        this.fileStreamProvider = fileStreamProvider;
        this.urlStreamProvider = urlStreamProvider;
        this.channelProvider = channelProvider;
    }

    public ImageDownloadExecutor(
            UniqueFileNameGeneratorFactory uniqueFileNameGeneratorFactory,
            FileStreamProviderFactory fileStreamProviderFactory,
            UrlStreamProviderFactory urlStreamProviderFactory,
            ChannelProviderFactory channelProviderFactory
    ) {
        this(
                uniqueFileNameGeneratorFactory.create(),
                fileStreamProviderFactory.create(),
                urlStreamProviderFactory.create(),
                channelProviderFactory.create()
        );
    }

    public ImageDownloadExecutor(DownloadExecutorFactoryContext downloadExecutorFactoryContext) {
        this(
                downloadExecutorFactoryContext.getUniqueFileNameGeneratorFactory(),
                downloadExecutorFactoryContext.getFileStreamProviderFactory(),
                downloadExecutorFactoryContext.getUrlStreamProviderFactory(),
                downloadExecutorFactoryContext.getChannelProviderFactory()
        );
    }

    public ImageDownloadExecutor() {
        this(DefaultDownloadExecutorFactoryContext.getInstance());
    }

    public File[] downloadTo(
            File targetFolder,
            ImageDataResponse response,
            Format format
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        for (var image : response.data()) {
            this.downloadTo(
                    targetFolder,
                    image,
                    format.val()
            );
        }

        return targetFolder.listFiles();
    }

    @Override
    public File downloadTo(
            File targetFolder,
            FileContentProvider result,
            String format
    ) throws IOException {
        var file = new File(targetFolder.getAbsoluteFile() + "/"
                                    + this.uniqueFileNameGenerator.generateRandomNamePrefix()
                                    + ".png");
        try (var fos = this.fileStreamProvider.createOutputStream(file)) {
            if (format.equals(Format.URL.val())) {
                var uri = this.urlStreamProvider.openStream(result.url());

                fos.getChannel()
                   .transferFrom(
                           this.channelProvider.createChannel(uri),
                           0,
                           Long.MAX_VALUE
                   );
            } else {
                fos.write(result.bytes());
            }
        }

        return file;
    }
}