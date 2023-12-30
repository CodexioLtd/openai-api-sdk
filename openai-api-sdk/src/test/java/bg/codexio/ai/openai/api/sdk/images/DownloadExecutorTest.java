package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.payload.images.response.ImageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DownloadExecutorTest {

    private static File TEST_TARGET_FOLDER;
    private static ImageResponse TEST_IMAGE_RESPONSE;
    private static UUID TEST_UUID;
    private static FileOutputStream TEST_OUTPUT_STREAM;
    private static FileChannel TEST_FILE_CHANNEL;
    private static Base64.Decoder TEST_DECODER;
    private static byte[] TEST_IMAGE_BYTES;
    private static ReadableByteChannel TEST_CHANNEL;
    private static URI TEST_URI;
    private static String EXPECTED_FILE_NAME;

    private static File executeWith(Format format) {
        try {
            return DownloadExecutor.FromFile.downloadFile(
                    TEST_TARGET_FOLDER,
                    format,
                    TEST_IMAGE_RESPONSE
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Arguments> provideCoverageVariables() {
        return Stream.of(
                Arguments.of(
                        true,
                        0,
                        Format.BASE_64
                ),
                Arguments.of(
                        true,
                        0,
                        Format.URL
                ),
                Arguments.of(
                        false,
                        1,
                        Format.BASE_64
                ),
                Arguments.of(
                        false,
                        1,
                        Format.URL
                )
        );
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        TEST_TARGET_FOLDER = mock(File.class);
        doReturn(TEST_TARGET_FOLDER).when(TEST_TARGET_FOLDER)
                                    .getAbsoluteFile();
        doReturn("test-path").when(TEST_TARGET_FOLDER)
                             .toString();

        TEST_IMAGE_RESPONSE = mock(ImageResponse.class);

        TEST_UUID = mock(UUID.class);
        doReturn("test-uuid").when(TEST_UUID)
                             .toString();

        EXPECTED_FILE_NAME = "test-path/test-uuid.png";
        TEST_OUTPUT_STREAM = mock(FileOutputStream.class);
        TEST_FILE_CHANNEL = mock(FileChannel.class);
        doReturn(TEST_FILE_CHANNEL).when(TEST_OUTPUT_STREAM)
                                   .getChannel();

        TEST_DECODER = mock(Base64.Decoder.class);
        TEST_IMAGE_BYTES = new byte[]{1, 2, 3};
        doReturn(TEST_IMAGE_BYTES).when(TEST_DECODER)
                                  .decode(nullable(String.class));

        TEST_CHANNEL = mock(ReadableByteChannel.class);

        TEST_URI = mock(URI.class);
        var url = mock(URL.class);
        doReturn(url).when(TEST_URI)
                     .toURL();
    }

    @ParameterizedTest
    @MethodSource("provideCoverageVariables")
    public void testDownloadTo_multipleFilesAndDifferentFormats_shouldCallDownloadFileCorrectly(
            boolean exists,
            int mkdirsCallTimes,
            Format format
    ) throws IOException {
        var firstImage = mock(ImageResponse.class);
        var secondImage = mock(ImageResponse.class);

        var firstResultFile = new File("first/file.png");
        var secondResultFile = new File("second/file.png");
        var listFiles = new File[]{firstResultFile, secondResultFile};

        var targetFolder = mock(File.class);
        when(targetFolder.exists()).thenReturn(exists);
        when(targetFolder.listFiles()).thenReturn(listFiles);

        var response = new ImageDataResponse(
                1L,
                List.of(
                        firstImage,
                        secondImage
                )
        );

        try (var fileUtils = mockStatic(DownloadExecutor.FromFile.class)) {
            var resultFiles = DownloadExecutor.FromResponse.downloadTo(
                    targetFolder,
                    response,
                    format
            );

            assertAll(
                    () -> fileUtils.verify(
                            () -> DownloadExecutor.FromFile.downloadFile(
                                    eq(targetFolder),
                                    eq(format),
                                    eq(firstImage)
                            ),
                            times(1)
                    ),
                    () -> fileUtils.verify(
                            () -> DownloadExecutor.FromFile.downloadFile(
                                    eq(targetFolder),
                                    eq(format),
                                    eq(secondImage)
                            ),
                            times(1)
                    ),
                    () -> verify(
                            targetFolder,
                            times(mkdirsCallTimes)
                    ).mkdirs(),
                    () -> assertEquals(
                            listFiles,
                            resultFiles
                    )
            );
        }
    }

    @Test
    public void testDownloadFile_expectBase64() {
        this.doTest(() -> {
            var file = executeWith(Format.BASE_64);

            assertAll(
                    () -> assertEquals(
                            EXPECTED_FILE_NAME.replace(
                                    "/",
                                    File.separator
                            ),
                            file.getPath()
                    ),
                    () -> verify(
                            TEST_IMAGE_RESPONSE,
                            times(1)
                    ).b64Json(),
                    () -> verify(
                            TEST_OUTPUT_STREAM,
                            times(1)
                    ).write(eq(TEST_IMAGE_BYTES))
            );
        });
    }

    @Test
    public void testDownloadFile_expectUrl() {
        this.doTest(() -> {
            var file = executeWith(Format.URL);

            assertAll(
                    () -> assertEquals(
                            EXPECTED_FILE_NAME.replace(
                                    "/",
                                    File.separator
                            ),
                            file.getPath()
                    ),
                    () -> verify(
                            TEST_IMAGE_RESPONSE,
                            times(1)
                    ).url(),
                    () -> verify(
                            TEST_FILE_CHANNEL,
                            times(1)
                    ).transferFrom(
                            eq(TEST_CHANNEL),
                            eq(0L),
                            eq(Long.MAX_VALUE)
                    )
            );
        });
    }

    @Test
    public void testOutputStream_nonExistingFile_expectException() {
        assertThrows(
                FileNotFoundException.class,
                () -> DownloadExecutor.Streams.outputStream(
                        "/non/existing/file/" + UUID.randomUUID() + ".noext")
        );
    }

    @Test
    public void testOutputStream_existingFile_expectCorrectOutputStream()
            throws FileNotFoundException, URISyntaxException {
        assertNotNull(DownloadExecutor.Streams.outputStream(this.getClass()
                                                                .getClassLoader()
                                                                .getResource(
                                                                        "fake-file.txt")
                                                                .toURI()
                                                                .getPath()));
    }

    private void doTest(Runnable runnable) {
        try (
                var uuidUtils = mockStatic(UUID.class);
                var streamUtils = mockStatic(DownloadExecutor.Streams.class);
                var uriUtils = mockStatic(URI.class);
                var channelUtils = mockStatic(Channels.class);
                var base64Utils = mockStatic(Base64.class)
        ) {
            uuidUtils.when(UUID::randomUUID)
                     .thenReturn(TEST_UUID);
            streamUtils.when(() -> DownloadExecutor.Streams.outputStream(eq(EXPECTED_FILE_NAME)))
                       .thenReturn(TEST_OUTPUT_STREAM);
            uriUtils.when(() -> URI.create(nullable(String.class)))
                    .thenReturn(TEST_URI);
            channelUtils.when(() -> Channels.newChannel(nullable(InputStream.class)))
                        .thenReturn(TEST_CHANNEL);
            base64Utils.when(Base64::getDecoder)
                       .thenReturn(TEST_DECODER);

            runnable.run();
        }
    }
}
