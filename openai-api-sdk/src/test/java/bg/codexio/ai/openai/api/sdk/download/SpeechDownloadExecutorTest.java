package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.sdk.download.name.UniqueFileNameGenerator;
import bg.codexio.ai.openai.api.sdk.download.stream.FileStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.download.InternalAssertions.*;
import static org.mockito.Mockito.mock;

public class SpeechDownloadExecutorTest {

    private SpeechDownloadExecutor speechDownloadExecutor;

    private UniqueFileNameGenerator uniqueFileNameGenerator;

    private FileStreamProvider fileStreamProvider;

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

    @BeforeEach
    void setUp() {
        this.uniqueFileNameGenerator = mock(UniqueFileNameGenerator.class);
        this.fileStreamProvider = mock(FileStreamProvider.class);
        this.speechDownloadExecutor = new SpeechDownloadExecutor(
                this.uniqueFileNameGenerator,
                this.fileStreamProvider
        );
    }

    @ParameterizedTest
    @MethodSource("provideCoverageVariables")
    public void testDownloadTo_expectCorrectFileNameAndTargetFolderCreation(
            boolean exists,
            int mkdirsCallTimes
    ) throws IOException {
        mockFileDownloading(
                exists,
                mkdirsCallTimes,
                this.uniqueFileNameGenerator,
                this.fileStreamProvider,
                this.speechDownloadExecutor,
                FILE_URL.concat("random-test-uuid".concat(".")
                                                  .concat(FILE_TEST_NAME))
        );
    }
}
