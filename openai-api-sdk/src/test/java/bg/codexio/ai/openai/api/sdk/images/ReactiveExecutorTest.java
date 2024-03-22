package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.*;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ReactiveExecutorTest<R extends ImageRequest> {

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
    public void testGet_expectResponseFromExecuteReactive(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder,
            Class<R> classType
    ) {
        when(executor.reactive()
                     .execute(any(classType))).thenAnswer(answer -> new ReactiveExecution<>(
                Flux.empty(),
                Mono.just(IMAGE_DATA_RESPONSE)
        ));

        var reactiveExecutor = new ReactiveExecutor<R>(
                executor,
                builder
        );
        var response = reactiveExecutor.get()
                                       .block();

        assertAll(() -> assertEquals(
                IMAGE_DATA_RESPONSE,
                response
        ));
    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testDownload_withImaginaryFolder_shouldDownloadToLocation(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder,
            Class<R> classType
    ) {
        var targetFolder = new File("imaginaryFolder");
        var path = TEST_FILE_PATH;

        when(executor.reactive()
                     .execute(any(classType))).thenAnswer(answer -> new ReactiveExecution<>(
                Flux.empty(),
                Mono.just(IMAGE_DATA_RESPONSE)
        ));

        try (var downloadUtils = mockStatic(DownloadExecutor.FromFile.class)) {
            downloadUtils.when(() -> DownloadExecutor.FromFile.downloadFile(
                                 eq(targetFolder),
                                 eq(Format.BASE_64),
                                 eq(IMAGE_RESPONSE)
                         ))
                         .thenReturn(new File(path));

            var reactiveExecutor = new ReactiveExecutor<R>(
                    executor,
                    builder
            );

            var result = reactiveExecutor.download(targetFolder)
                                         .map(File::getPath)
                                         .blockFirst();

            assertEquals(
                    path.replace(
                            "/",
                            File.separator
                    ),
                    result
            );
        }
    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testDownload_withDownloadExecutorFail_shouldThrowError(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder,
            Class<R> classType
    ) {
        var reactiveExecutor = new ReactiveExecutor<>(
                executor,
                builder
        );
        var targetFolder = new File("imaginaryFolder");

        when(executor.reactive()
                     .execute(any(classType))).thenAnswer(answer -> new ReactiveExecution<>(
                Flux.empty(),
                Mono.just(IMAGE_DATA_RESPONSE)
        ));

        try (var downloadUtils = mockStatic(DownloadExecutor.FromFile.class)) {
            downloadUtils.when(() -> DownloadExecutor.FromFile.downloadFile(
                                 eq(targetFolder),
                                 eq(Format.BASE_64),
                                 eq(IMAGE_RESPONSE)
                         ))
                         .thenThrow(new IOException("Simulated IOException"));

            assertThrows(
                    RuntimeException.class,
                    () -> reactiveExecutor.download(targetFolder)
                                          .map(File::getPath)
                                          .blockFirst()

            );
        }
    }
}

