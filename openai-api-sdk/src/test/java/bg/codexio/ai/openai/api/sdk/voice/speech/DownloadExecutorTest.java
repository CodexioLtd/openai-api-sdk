package bg.codexio.ai.openai.api.sdk.voice.speech;


import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.response.AudioBinaryResponse;
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

import static org.junit.jupiter.api.Assertions.*;
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
        when(uuidMock.toString()).thenReturn("random-uuid");

        var targetFolder = mock(File.class);
        when(targetFolder.getAbsolutePath()).thenReturn("/target/folder");
        when(targetFolder.exists()).thenReturn(exists);

        var mockStream = mock(FileOutputStream.class);

        var expectedFileName = "/target/folder/random-uuid.mp3";

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
                    new AudioBinaryResponse(new byte[]{1, 2, 3}),
                    AudioFormat.MP3.val()
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
                    ).write(eq(new byte[]{1, 2, 3}))
            );
        }
    }

    @Test
    public void testOutputStream_nonExistingFile_expectException() {
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
