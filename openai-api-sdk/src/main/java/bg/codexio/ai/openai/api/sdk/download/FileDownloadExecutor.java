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

public class FileDownloadExecutor
        implements DownloadExecutor {

    private final UniqueFileNameGenerator uniqueFileNameGenerator;

    private final FileStreamProvider fileStreamProvider;

    public FileDownloadExecutor(
            UniqueFileNameGenerator uniqueFileNameGenerator,
            FileStreamProvider fileStreamProvider
    ) {
        this.uniqueFileNameGenerator = uniqueFileNameGenerator;
        this.fileStreamProvider = fileStreamProvider;
    }

    public FileDownloadExecutor(
            UniqueFileNameGeneratorFactory uniqueFileNameGeneratorFactory,
            FileStreamProviderFactory fileStreamProviderFactory
    ) {
        this(
                uniqueFileNameGeneratorFactory.create(),
                fileStreamProviderFactory.create()
        );
    }

    public FileDownloadExecutor(DownloadExecutorFactoryContext downloadExecutorFactoryContext) {
        this(
                downloadExecutorFactoryContext.getUniqueFileNameGeneratorFactory(),
                downloadExecutorFactoryContext.getFileStreamProviderFactory()
        );
    }

    public FileDownloadExecutor() {
        this(DefaultDownloadExecutorFactoryContext.getInstance());
    }

    public File downloadTo(
            File targetFolder,
            FileContentProvider result,
            String fileName
    ) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        var uniqueFileName =
                this.uniqueFileNameGenerator.generateRandomNamePrefix()
                                                         .concat(fileName);
        var targetFile = new File(targetFolder.getAbsolutePath()
                                              .concat("/".concat(uniqueFileName)));

        try (var os = this.fileStreamProvider.createOutputStream(targetFile)) {
            os.write(result.bytes());
        }

        return targetFile;
    }
}