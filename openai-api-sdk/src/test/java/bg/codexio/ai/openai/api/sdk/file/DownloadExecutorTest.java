package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_TEST_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DownloadExecutorTest {

    private static Stream<Arguments> provideCoverageVariables() {
        return Stream.of(
                Arguments.of(
                        true,
                        0
                ),
                Arguments.of(
                        false,
                        1
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideCoverageVariables")
    public void testDownloadTo_expectCorrectFileNameAndTargetFolderCreation(
            boolean exists,
            int mkdirsCallTimes
    ) throws IOException {
        var uuidMock = mock(UUID.class);
        var targetFolder = mock(File.class);
        var fileUrl = "/target/folder/";
        var fileContentBytes = new byte[]{1, 2, 3};

        when(uuidMock.toString()).thenReturn("random-test-uuid");
        when(targetFolder.getAbsolutePath()).thenReturn(fileUrl);
        when(targetFolder.exists()).thenReturn(exists);

        var mockStream = mock(FileOutputStream.class);
        var expectedFileName = fileUrl.concat("random".concat(FILE_TEST_NAME));

        try (
                var uuidUtils = mockStatic(UUID.class);
                var streamUtils = mockStatic(DownloadExecutor.Streams.class)
        ) {
            uuidUtils.when(UUID::randomUUID)
                     .thenReturn(uuidMock);
            streamUtils.when(() -> DownloadExecutor.Streams.outputStream(any()))
                       .thenReturn(mockStream);

            var result = DownloadExecutor.downloadTo(
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
                            mockStream,
                            times(1)
                    ).write(eq(fileContentBytes))
            );
        }
    }

    @Test
    public void testOutputStream_withNonExistingFile_expectFileNotFoundException() {
        assertThrows(
                FileNotFoundException.class,
                () -> DownloadExecutor.Streams.outputStream(new File("/non/existing/file/".concat(FILE_TEST_NAME)))
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