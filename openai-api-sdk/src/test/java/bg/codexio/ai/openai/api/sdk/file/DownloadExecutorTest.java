package bg.codexio.ai.openai.api.sdk.file;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DownloadExecutorTest {

    // test successful case
    @Test
    public void testOutputStream_withNonExistingFile_expectFileNotFoundException() {
        assertThrows(
                FileNotFoundException.class,
                () -> DownloadExecutor.Streams.outputStream(new File(
                        "/non/existing/file/" + UUID.randomUUID() + ".noext"))
        );
    }

    @Test
    public void testOutputStream_existingFile_expectCorrectOutputStream()
            throws FileNotFoundException, URISyntaxException {
        assertNotNull(DownloadExecutor.Streams.outputStream(new File(this.getClass()
                                                                         .getClassLoader()
                                                                         .getResource("fake-file.txt")
                                                                         .toURI()
                                                                         .getPath())));
    }
}
