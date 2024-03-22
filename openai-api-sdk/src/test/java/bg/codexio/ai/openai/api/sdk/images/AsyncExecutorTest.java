package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.AsynchronousHttpExecutor;
import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.*;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AsyncExecutorTest<R extends ImageRequest> {

    private static final File TARGET_FOLDER = new File("imaginaryFolder");
    private static final File FIRST_FILE = new File("/var/files/firstFile");
    private static final File SECOND_FILE = new File("/var/files/secondFile");
    private static final MockedStatic<LoggerFactory> LOGGER_FACTORY_UTILS =
            mockStatic(LoggerFactory.class);
    private static final Logger MOCK_LOGGER = mock(Logger.class);

    private static final Consumer<String> MOCK_CONSUMER = mock(Consumer.class);

    static {
        LOGGER_FACTORY_UTILS.when(() -> LoggerFactory.getLogger(eq(AsyncExecutor.class)))
                            .thenReturn(MOCK_LOGGER);
    }

    private static Stream<Arguments> provideActionTypeInstances() {
        var executors = Arrays.asList(
                CREATE_IMAGE_HTTP_EXECUTOR,
                EDIT_IMAGE_HTTP_EXECUTOR,
                IMAGE_VARIATION_HTTP_EXECUTOR
        );

        var builders = Arrays.asList(
                CREATE_IMAGE_REQUEST_BUILDER,
                EDIT_IMAGE_REQUEST_BUILDER,
                IMAGE_VARIATION_REQUEST_BUILDER
        );

        var classes = Arrays.asList(
                CreateImageRequest.class,
                EditImageRequest.class,
                ImageVariationRequest.class
        );

        return Stream.of(
                Arguments.of(
                        executors.get(0),
                        builders.get(0),
                        classes.get(0)
                ),
                Arguments.of(
                        executors.get(1),
                        builders.get(1),
                        classes.get(1)
                ),
                Arguments.of(
                        executors.get(2),
                        builders.get(2),
                        classes.get(2)
                )
        );
    }


    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testOnResponse_withSimpleFinalizerCallback_responseShouldBeAccepted(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        var responseHandle =
                AsyncCallbackUtils.prepareCallback(ImageDataResponse.class);

        this.mockExecutorFlow(executor);

        var asyncExecutor = new AsyncExecutor<>(
                executor,
                builder
        );

        asyncExecutor.onResponse(responseHandle.callback());

        verifyNoInteractions(MOCK_CONSUMER);

        assertEquals(
                IMAGE_DATA_RESPONSE,
                responseHandle.data()
        );
    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testWhenDownloaded_withSimpleFinalizerCallback_responseShouldBeAcceptedAndTransformedToFileArray(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        var fileHandle = AsyncCallbackUtils.prepareCallback(File[].class);

        this.mockExecutorFlow(executor);

        var downloadedFiles = new File[]{FIRST_FILE, SECOND_FILE};

        try (
                var fileUtils = mockStatic(DownloadExecutor.FromResponse.class);
                var futureUtils = mockStatic(CompletableFuture.class)
        ) {
            this.mockDownloadFlowSuccess(
                        fileUtils,
                        futureUtils
                )
                .thenReturn(downloadedFiles);

            var asyncExecutor = new AsyncExecutor<>(
                    executor,
                    builder
            );

            asyncExecutor.whenDownloaded(
                    TARGET_FOLDER,
                    fileHandle.callback()
            );

            verifyNoInteractions(MOCK_CONSUMER);

            assertEquals(
                    downloadedFiles,
                    fileHandle.data()
            );
        }
    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testWhenDownloaded_withSimpleFinalizerCallback_shouldThrowADownloadErrorAndBeHandled(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        var fileHandle = AsyncCallbackUtils.prepareCallback(File[].class);

        this.mockExecutorFlow(executor);

        var exception = new IOException();

        try (
                var fileUtils = mockStatic(DownloadExecutor.FromResponse.class);
                var futureUtils = mockStatic(CompletableFuture.class)
        ) {
            this.mockDownloadFlowSuccess(
                        fileUtils,
                        futureUtils
                )
                .thenThrow(exception);

            var asyncExecutor = new AsyncExecutor<>(
                    executor,
                    builder
            );

            verifyNoInteractions(MOCK_CONSUMER);

            assertThrows(
                    RuntimeException.class,
                    () -> asyncExecutor.whenDownloaded(
                            TARGET_FOLDER,
                            fileHandle.callback()
                    )
            );
        }

    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testWhenDownloaded_withSimpleFinalizerCallback_shouldThrowACompletionErrorAndBeHandled(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        var fileHandle = AsyncCallbackUtils.prepareCallback(File[].class);
        var errorHandle = AsyncCallbackUtils.prepareCallback(Throwable.class);

        this.mockExecutorFlow(executor);

        try (
                var futureUtils = mockStatic(CompletableFuture.class)
        ) {
            var exception = this.mockDownloadFlowError(futureUtils);

            var asyncExecutor = new AsyncExecutor<>(
                    executor,
                    builder
            );

            asyncExecutor.whenDownloaded(
                    TARGET_FOLDER,
                    fileHandle.callback(),
                    errorHandle.callback()
            );

            verifyNoInteractions(MOCK_CONSUMER);

            assertEquals(
                    exception,
                    errorHandle.data()
            );
        }
    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testWhenDownloaded_withSimpleFinalizerCallback_shouldThrowACompletionErrorAndBeHandledWithDefaultCallback(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        var fileHandle = AsyncCallbackUtils.prepareCallback(File[].class);

        this.mockExecutorFlow(executor);

        try (
                var futureUtils = mockStatic(CompletableFuture.class)
        ) {
            var exception = this.mockDownloadFlowError(futureUtils);

            var asyncExecutor = new AsyncExecutor<>(
                    executor,
                    builder
            );

            asyncExecutor.whenDownloaded(
                    TARGET_FOLDER,
                    fileHandle.callback()
            );

            assertAll(
                    () -> verifyNoInteractions(MOCK_CONSUMER),
                    () -> verify(
                            MOCK_LOGGER,
                            times(1)
                    ).error(
                            anyString(),
                            eq(exception)
                    )
            );
        }
    }

    private OngoingStubbing<Object> mockDownloadFlowSuccess(
            MockedStatic<DownloadExecutor.FromResponse> fileUtils,
            MockedStatic<CompletableFuture> futureUtils
    ) {
        var spiedFuture = spy(new CompletableFuture<>());

        futureUtils.when(() -> CompletableFuture.supplyAsync(any()))
                   .thenAnswer(invocation -> {
                       var supplier = invocation.getArgument(
                               0,
                               Supplier.class
                       );

                       doAnswer(spyInvocation -> {
                           var action = spyInvocation.getArgument(
                                   0,
                                   BiConsumer.class
                           );
                           action.accept(
                                   supplier.get(),
                                   null
                           );
                           return null;
                       }).when(spiedFuture)
                         .whenComplete(any());

                       return spiedFuture;
                   });

        return fileUtils.when(() -> DownloadExecutor.FromResponse.downloadTo(
                any(),
                any(),
                any()
        ));
    }

    private Throwable mockDownloadFlowError(
            MockedStatic<CompletableFuture> futureUtils
    ) {
        var exception = new RuntimeException("Error downloading file.");
        var spiedFuture = spy(new CompletableFuture<>());
        spiedFuture.completeExceptionally(exception);

        futureUtils.when(() -> CompletableFuture.supplyAsync(any()))
                   .thenReturn(spiedFuture);

        return exception;
    }

    private void mockExecutorFlow(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor
    ) {
        var asyncExecutor = mock(AsynchronousHttpExecutor.class);
        when(executor.async()).thenReturn(asyncExecutor);
        doAnswer(invocation -> {
            invocation.getArgument(
                              2,
                              Consumer.class
                      )
                      .accept(IMAGE_DATA_RESPONSE);
            return null;
        }).when(asyncExecutor)
          .execute(
                  any(),
                  any(),
                  any()
          );
    }
}

