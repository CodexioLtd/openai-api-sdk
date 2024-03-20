package bg.codexio.ai.openai.api.sdk.download.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public interface FileStreamProvider {
    FileOutputStream createOutputStream(File file) throws FileNotFoundException;
}
