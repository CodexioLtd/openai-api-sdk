package bg.codexio.ai.openai.api.sdk.file.download;

import org.junit.jupiter.api.BeforeEach;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_DOWNLOADING_META_WITHOUT_FOLDER;

public class FileDownloadingImmediateContextStageTest {

    private FileDownloadingImmediateContextStage fileDownloadingStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingStage = new FileDownloadingImmediateContextStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FILE_DOWNLOADING_META_WITHOUT_FOLDER
        );
    }
    //
    //    @Test
    //    void testToFolder_expectCorrectResponse() {
    //        when(RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR.immediate()
    //                                                .retrieve(any()))
    //                                                .thenReturn
    //                                                (FILE_CONTENT_RESPONSE);
    //
    //        mockDownloadExecutor(() -> {
    //            try {
    //                assertEquals(
    //                        FILE_TEST_PATH.replace(
    //                                "/",
    //                                File.separator
    //                        ),
    //                        this.fileDownloadingStage.toFolder(FILE)
    //                                                 .getPath()
    //                );
    //            } catch (IOException e) {
    //                throw new RuntimeException(e);
    //            }
    //        });
    //    }
}