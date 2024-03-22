package bg.codexio.ai.openai.api.sdk.file.download;

import org.junit.jupiter.api.BeforeEach;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_DOWNLOADING_META_WITH_FULL_DATA;

public class FileDownloadingAsyncPromiseStageTest {

    private FileDownloadingAsyncPromise fileDownloadingAsyncPromise;

    //    private static void mockExecutorToExecuteCallbacks(
    //            boolean error,
    //            Runnable execution
    //    ) throws JsonProcessingException {
    //        var spiedFuture = spy(new CompletableFuture<>());
    //
    //        try (
    //                var downloadUtils = mockStatic(DownloadExecutor.class);
    //                var futureUtils = mockStatic(CompletableFuture.class)
    //        ) {
    //
    //            futureUtils.when(() -> CompletableFuture.supplyAsync(any()))
    //                       .thenAnswer(invocation -> {
    //                           var supplier = invocation.getArgument(
    //                                   0,
    //                                   Supplier.class
    //                           );
    //                           spiedFuture.complete(supplier.get());
    //
    //                           return spiedFuture;
    //                       });
    //
    //            var downloadStub =
    //                    downloadUtils.when(() -> DownloadExecutor.downloadTo(
    //                    any(),
    //                    any(),
    //                    any()
    //            ));
    //            if (error) {
    //                downloadStub.thenThrow(new IOException("Error while
    //                downloading."));
    //            } else {
    //                downloadStub.thenReturn(new File(FILE_TEST_PATH));
    //            }
    //
    //            mockRetrieve(
    //                    RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
    //                    FILE_CONTENT_RESPONSE,
    //                    OBJECT_MAPPER.writeValueAsString
    //                    (FILE_CONTENT_RESPONSE)
    //            );
    //
    //            execution.run();
    //        }
    //    }

    @BeforeEach
    void setUp() {
        this.fileDownloadingAsyncPromise = new FileDownloadingAsyncPromise(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FILE_DOWNLOADING_META_WITH_FULL_DATA
        );
    }
    //
    //    @Test
    //    void testWhenDownloaded_withCallBack_expectCorrectResponse()
    //            throws JsonProcessingException {
    //        mockExecutorToExecuteCallbacks(
    //                false,
    //                () -> {
    //                    var finalizerData = prepareCallback(File.class);
    //                    this.fileDownloadingAsyncPromise.whenDownloaded
    //                    (finalizerData.callback());
    //                    verify(finalizerData.callback()).accept(any());
    //                }
    //        );
    //    }
    //
    //    @Test
    //    void testWhenDownloaded_withCallBackAndError_expectErrorResponse()
    //            throws JsonProcessingException {
    //        mockExecutorToExecuteCallbacks(
    //                true,
    //                () -> {
    //                    var finalizerData = prepareCallback(File.class);
    //                    var error = prepareCallback(Throwable.class);
    //                    assertThrows(
    //                            RuntimeException.class,
    //                            () -> this.fileDownloadingAsyncPromise
    //                            .whenDownloaded(
    //                                    finalizerData.callback(),
    //                                    error.callback()
    //                            )
    //                    );
    //                }
    //        );
    //    }
    //
    //    @Test
    //    void
    //    testWhenDownloaded_withCallBackAndErrorHandler_expectCorrectResponse()
    //            throws JsonProcessingException {
    //        mockExecutorToExecuteCallbacks(
    //                false,
    //                () -> {
    //                    var finalizerData = prepareCallback(File.class);
    //                    var errorData = prepareCallback(Throwable.class);
    //
    //                    this.fileDownloadingAsyncPromise.whenDownloaded(
    //                            finalizerData.callback(),
    //                            errorData.callback()
    //                    );
    //
    //                    assertAll(
    //                            () -> verify(finalizerData.callback())
    //                            .accept(any()),
    //                            () -> verifyNoInteractions(errorData
    //                            .callback())
    //                    );
    //                }
    //        );
    //    }
}
