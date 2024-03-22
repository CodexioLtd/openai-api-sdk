package bg.codexio.ai.openai.api.sdk.file.download;

import org.junit.jupiter.api.BeforeEach;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_DOWNLOADING_META_WITHOUT_FOLDER;

public class FileDownloadingReactiveContextStageTest {

    private FileDownloadingReactiveContextStage fileDownloadingReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingReactiveContextStage =
                new FileDownloadingReactiveContextStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FILE_DOWNLOADING_META_WITHOUT_FOLDER
        );
    }

    //    @Test
    //    public void testToFolder_expectCorrectResponse() {
    //        when(this.fileDownloadingReactiveContextStage.executor.reactive()
    //                                                              .retrieve
    //                                                              (any()))
    //                                                              .thenAnswer(res -> new ReactiveExecution<>(
    //                Flux.empty(),
    //                Mono.just(FILE_CONTENT_RESPONSE)
    //        ));
    //
    //        mockDownloadExecutor(() -> assertEquals(
    //                FILE_TEST_PATH.replace(
    //                        "/",
    //                        File.separator
    //                ),
    //                this.fileDownloadingReactiveContextStage.toFolder(FILE)
    //                                                        .block()
    //                                                        .getPath()
    //        ));
    //    }
}
