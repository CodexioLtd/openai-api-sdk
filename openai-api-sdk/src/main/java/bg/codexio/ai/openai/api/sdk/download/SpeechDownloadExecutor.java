package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.FileContentProvider;
import bg.codexio.ai.openai.api.sdk.download.context.DefaultDownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.context.DownloadExecutorFactoryContext;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGenerator;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGeneratorFactory;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProvider;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProviderFactory;

import java.io.File;
import java.io.IOException;

public class SpeechDownloadExecutor
        implements DownloadExecutor {

    private final UniqueFileNameGenerator uniqueFileNameGenerator;

    private final FileStreamProvider fileStreamProvider;

    public SpeechDownloadExecutor(
            UniqueFileNameGenerator uniqueFileNameGenerator,
            FileStreamProvider fileStreamProvider
    ) {
        this.uniqueFileNameGenerator = uniqueFileNameGenerator;
        this.fileStreamProvider = fileStreamProvider;
    }

    public SpeechDownloadExecutor(
            UniqueFileNameGeneratorFactory uniqueFileNameGeneratorFactory,
            FileStreamProviderFactory fileStreamProviderFactory
    ) {
        this(
                uniqueFileNameGeneratorFactory.create(),
                fileStreamProviderFactory.create()
        );
    }

    public SpeechDownloadExecutor(DownloadExecutorFactoryContext downloadExecutorFactoryContext) {
        this(
                downloadExecutorFactoryContext.getUniqueFileNameGeneratorFactory(),
                downloadExecutorFactoryContext.getFileStreamProviderFactory()
        );
    }

    public SpeechDownloadExecutor() {
        this(DefaultDownloadExecutorFactoryContext.getInstance());
    }

    /**
     * @param targetFolder where to download the file
     * @param result       a result given by the OpenAI API
     * @param mediaType    the extension of the file to be saved
     * @return the downloaded {@link File} with random UUID name.
     * @throws IOException if the target folder does not exist, or it's not
     *                     writable
     */
    @Override
    public File downloadTo(
            File targetFolder,
            FileContentProvider result,
            String mediaType
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        var targetFile = new File(targetFolder.getAbsolutePath() + "/"
                                          + this.uniqueFileNameGenerator.generateRandomNamePrefix()
                                          + "." + mediaType);

        try (var os = this.fileStreamProvider.createOutputStream(targetFile)) {
            os.write(result.bytes());
        }

        return targetFile;
    }
}