package bg.codexio.ai.openai.api.sdk.download.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamProvider
        implements FileStreamProvider {
    @Override
    public FileOutputStream createOutputStream(File file)
            throws FileNotFoundException {
        return new FileOutputStream(file);
    }
}