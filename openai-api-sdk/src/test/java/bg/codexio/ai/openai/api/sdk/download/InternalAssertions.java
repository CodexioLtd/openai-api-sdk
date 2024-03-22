package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGenerator;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public final class InternalAssertions {

    static final String FILE_TEST_NAME = "test_name";

    static final String FILE_URL = "/target/folder/";

    private InternalAssertions() {
    }

    static void mockFileDownloading(
            boolean exists,
            int mkdirsCallTimes,
            UniqueFileNameGenerator uniqueFileNameGenerator,
            FileStreamProvider fileStreamProvider,
            DownloadExecutor downloadExecutor,
            String expectedFileName
    ) throws IOException {
        var fileContentBytes = new byte[]{1, 2, 3};
        var targetFolder = mock(File.class);
        var mockStream = mock(FileOutputStream.class);
        when(uniqueFileNameGenerator.generateRandomNamePrefix()).thenReturn(
                "random-test-uuid");
        when(targetFolder.getAbsolutePath()).thenReturn(FILE_URL);
        when(targetFolder.exists()).thenReturn(exists);
        when(fileStreamProvider.createOutputStream(any())).thenReturn(mockStream);

        var result = downloadExecutor.downloadTo(
                targetFolder,
                new FileContentResponse(fileContentBytes),
                FILE_TEST_NAME
        );

        assertAll(
                () -> verify(
                        targetFolder,
                        times(mkdirsCallTimes)
                ).mkdirs(),
                () -> assertEquals(
                        expectedFileName.replace(
                                "/",
                                File.separator
                        ),
                        result.getPath()
                ),
                () -> verify(
                        fileStreamProvider.createOutputStream(FILE),
                        times(1)
                ).write(eq(fileContentBytes))
        );
    }
}
