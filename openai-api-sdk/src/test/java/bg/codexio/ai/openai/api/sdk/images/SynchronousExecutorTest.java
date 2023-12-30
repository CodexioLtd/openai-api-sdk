package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.*;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class SynchronousExecutorTest<R extends ImageRequest> {

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
    public void testAndGetRaw_expectResponseFromExecutor(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder,
            Class<R> classType
    ) {
        when(executor.execute(any(classType))).thenAnswer(answer -> IMAGE_DATA_RESPONSE);

        var synchronousExecutor = new SynchronousExecutor<R>(
                executor,
                builder
        );
        var response = synchronousExecutor.andGetRaw();

        assertAll(() -> assertEquals(
                IMAGE_DATA_RESPONSE,
                response
        ));
    }

    @ParameterizedTest
    @MethodSource("provideActionTypeInstances")
    public void testAndDownload_withImaginaryFolder_shouldDownloadToLocation(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) throws IOException {
        var targetFolder = new File("imaginaryFolder");
        var path = TEST_FILE_PATH;
        var response = new File[]{new File(path)};

        when(executor.execute(any())).thenReturn(IMAGE_DATA_RESPONSE);

        var synchronousExecutor = new SynchronousExecutor<R>(
                executor,
                builder
        );

        try (
                var downloadUtils =
                        mockStatic(DownloadExecutor.FromResponse.class)
        ) {
            downloadUtils.when(() -> DownloadExecutor.FromResponse.downloadTo(
                                 eq(targetFolder),
                                 eq(IMAGE_DATA_RESPONSE),
                                 eq(Format.BASE_64)
                         ))
                         .thenReturn(response);

            var result = synchronousExecutor.andDownload(targetFolder);

            assertEquals(
                    path.replace(
                            "/",
                            File.separator
                    ),
                    result[0].getPath()
            );
        }
    }

}

