package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.payload.images.response.ImageResponse;
import bg.codexio.ai.openai.api.sdk.download.channel.ChannelProvider;
import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGenerator;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProvider;
import bg.codexio.ai.openai.api.sdk.download.url.UrlStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageDownloadExecutorTest {


    private UniqueFileNameGenerator uniqueFileNameGenerator;


    private FileStreamProvider fileStreamProvider;


    private UrlStreamProvider urlStreamProvider;


    private ChannelProvider channelProvider;

    private ImageDownloadExecutor imageDownloadExecutor;

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

    private static Stream<Arguments> provideStringImageFormats() {
        return Stream.of(
                Arguments.of(
                        Format.URL.val(),
                        1
                ),
                Arguments.of(
                        Format.BASE_64.val(),
                        0
                )
        );
    }

    @BeforeEach
    void setUp() {
        this.uniqueFileNameGenerator = mock(UniqueFileNameGenerator.class);
        this.fileStreamProvider = mock(FileStreamProvider.class);
        this.urlStreamProvider = mock(UrlStreamProvider.class);
        this.channelProvider = mock(ChannelProvider.class);
        this.imageDownloadExecutor = new ImageDownloadExecutor(
                this.uniqueFileNameGenerator,
                this.fileStreamProvider,
                this.urlStreamProvider,
                this.channelProvider
        );
    }

    @ParameterizedTest
    @MethodSource("provideCoverageVariables")
    public void testDownloadTo_multipleFilesAndDifferentFormats_expectCorrectResult(
            boolean exists,
            int mkdirsCallTimes,
            Format format
    ) throws IOException {
        var firstImage = mock(ImageResponse.class);
        var secondImage = mock(ImageResponse.class);

        var firstResultFile = new File("first/file.png");
        var secondResultFile = new File("second/file.png");
        var listFiles = new File[]{firstResultFile, secondResultFile};
        var mockStream = mock(FileOutputStream.class);

        var targetFolder = mock(File.class);
        try (var fileChannel = mock(FileChannel.class)) {
            when(targetFolder.exists()).thenReturn(exists);
            when(targetFolder.listFiles()).thenReturn(listFiles);
            when(this.fileStreamProvider.createOutputStream(any())).thenReturn(mockStream);
            when(targetFolder.getAbsoluteFile()).thenReturn(FILE);
            when(mockStream.getChannel()).thenReturn(fileChannel);
            when(fileChannel.transferFrom(
                    any(),
                    eq(0),
                    eq(Long.MAX_VALUE)
            )).thenReturn(1L);
            var response = new ImageDataResponse(
                    1L,
                    List.of(
                            firstImage,
                            secondImage
                    )
            );

            var resultFiles = this.imageDownloadExecutor.downloadTo(
                    targetFolder,
                    response,
                    format
            );

            assertAll(
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


    @ParameterizedTest
    @MethodSource("provideStringImageFormats")
    public void testDownloadTo_withStringFormat_expectCorrectResult(
            String format,
            int callTimes
    ) throws IOException {
        var targetFolder = mock(File.class);
        var mockStream = mock(FileOutputStream.class);
        var expectedName = "prefix.png";

        try (var fileChannel = mock(FileChannel.class)) {
            when(targetFolder.getAbsoluteFile()).thenReturn(new File("images/"));
            when(this.uniqueFileNameGenerator.generateRandomNamePrefix()).thenReturn("prefix");
            when(this.fileStreamProvider.createOutputStream(any())).thenReturn(mockStream);
            when(mockStream.getChannel()).thenReturn(fileChannel);
            when(fileChannel.transferFrom(
                    any(),
                    eq(0),
                    eq(Long.MAX_VALUE)
            )).thenReturn(1L);

            var result = this.imageDownloadExecutor.downloadTo(
                    targetFolder,
                    mock(ImageResponse.class),
                    format
            );

            assertAll(
                    () -> assertEquals(
                            expectedName,
                            result.getName()
                    ),
                    () -> verify(
                            mockStream,
                            times(callTimes)
                    ).getChannel()
            );
        }
    }
}
