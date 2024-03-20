package bg.codexio.ai.openai.api.sdk.download.stream;

public class FileOutputStreamProviderFactory
        implements FileStreamProviderFactory {

    @Override
    public FileStreamProvider create() {
        return new FileOutputStreamProvider();
    }
}