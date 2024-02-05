package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.images.CreateImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.EditImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.ImageVariationHttpExecutor;
import bg.codexio.ai.openai.api.models.dalle.DallE20;
import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.Quality;
import bg.codexio.ai.openai.api.payload.images.Style;
import bg.codexio.ai.openai.api.payload.images.request.*;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.payload.images.response.ImageResponse;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InternalAssertions {
    static final CreateImageHttpExecutor CREATE_IMAGE_HTTP_EXECUTOR =
            mock(CreateImageHttpExecutor.class);
    static final EditImageHttpExecutor EDIT_IMAGE_HTTP_EXECUTOR =
            mock(EditImageHttpExecutor.class);
    static final ImageVariationHttpExecutor IMAGE_VARIATION_HTTP_EXECUTOR =
            mock(ImageVariationHttpExecutor.class);

    static final Function<ImageRequestBuilder<CreateImageRequest>,
            CreateImageRequest> CREATE_IMAGE_REQUEST_FUNCTION =
            x -> new CreateImageRequest(
            x.prompt(),
            x.model(),
            x.n(),
            x.quality(),
            x.responseFormat(),
            x.size(),
            x.style(),
            x.user()
    );
    static final Function<ImageRequestBuilder<EditImageRequest>,
            EditImageRequest> EDIT_IMAGE_REQUEST_FUNCTION =
            x -> new EditImageRequest(
            x.image(),
            x.prompt(),
            x.mask(),
            x.model(),
            x.n(),
            x.size(),
            x.responseFormat(),
            x.user()
    );
    static final Function<ImageRequestBuilder<ImageVariationRequest>,
            ImageVariationRequest> IMAGE_VARIATION_REQUEST_FUNCTION =
            x -> new ImageVariationRequest(
            x.image(),
            x.prompt(),
            x.model(),
            x.n(),
            x.size(),
            x.responseFormat(),
            x.user()
    );

    static final Function<ImageRequestBuilder<CreateImageRequest>,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> PROMPTFUL_CREATE_RUNTIME_SELECTION = builder -> new PromptfulImagesRuntimeSelectionStage<>(
            CREATE_IMAGE_HTTP_EXECUTOR,
            builder
    );

    static final Function<ImageRequestBuilder<EditImageRequest>,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> PROMPTFUL_EDIT_RUNTIME_SELECTION = builder -> new PromptfulImagesRuntimeSelectionStage<>(
            EDIT_IMAGE_HTTP_EXECUTOR,
            builder
    );

    static final Function<ImageRequestBuilder<ImageVariationRequest>,
            PromptfulImagesRuntimeSelectionStage<ImageVariationRequest>> PROMPTLESS_VARIATIONS_RUNTIME_SELECTION = builder -> new PromptfulImagesRuntimeSelectionStage<>(
            IMAGE_VARIATION_HTTP_EXECUTOR,
            builder
    );
    static final ImageResponse IMAGE_RESPONSE = new ImageResponse(
            "test-base64",
            "test-url",
            "test-revised-prompt"
    );
    static final ImageDataResponse IMAGE_DATA_RESPONSE = new ImageDataResponse(
            new Date().getTime(),
            Collections.singletonList(IMAGE_RESPONSE)
    );
    static final String TEST_FILE_PATH = "test/file/path";
    static final String TEST_CUSTOM_SIZE = "100x100";
    static final String TEST_PROMPT = "test prompt";
    static final ImageRequestBuilder<CreateImageRequest> CREATE_IMAGE_REQUEST_BUILDER = ImageRequestBuilder.<CreateImageRequest>builder()
                                                                                                           .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION)
                                                                                                           .withModel(new DallE30().name())
                                                                                                           .withN(1)
                                                                                                           .withSize(InternalAssertions.TEST_CUSTOM_SIZE)
                                                                                                           .withQuality(Quality.HIGH_QUALITY.val())
                                                                                                           .withStyle(Style.HYPER_REAL.val())
                                                                                                           .withResponseFormat(Format.BASE_64.val())
                                                                                                           .withPrompt(InternalAssertions.TEST_PROMPT);
    static final ImageRequestBuilder<EditImageRequest> EDIT_IMAGE_REQUEST_BUILDER = ImageRequestBuilder.<EditImageRequest>builder()
                                                                                                       .withSpecificRequestCreator(EDIT_IMAGE_REQUEST_FUNCTION)
                                                                                                       .withModel(new DallE20().name())
                                                                                                       .withImage(new File(InternalAssertions.TEST_FILE_PATH))
                                                                                                       .withN(5)
                                                                                                       .withSize(InternalAssertions.TEST_CUSTOM_SIZE)
                                                                                                       .withResponseFormat(Format.BASE_64.val())
                                                                                                       .withPrompt(InternalAssertions.TEST_PROMPT);
    static final ImageRequestBuilder<ImageVariationRequest> IMAGE_VARIATION_REQUEST_BUILDER = ImageRequestBuilder.<ImageVariationRequest>builder()
                                                                                                                 .withSpecificRequestCreator(IMAGE_VARIATION_REQUEST_FUNCTION)
                                                                                                                 .withModel(new DallE20().name())
                                                                                                                 .withImage(new File(InternalAssertions.TEST_FILE_PATH))
                                                                                                                 .withMask(new File(InternalAssertions.TEST_FILE_PATH))
                                                                                                                 .withN(5)
                                                                                                                 .withSize(InternalAssertions.TEST_CUSTOM_SIZE)
                                                                                                                 .withResponseFormat(Format.BASE_64.val())
                                                                                                                 .withPrompt(InternalAssertions.TEST_PROMPT);
    static final int TEST_CHOICES = 5;

    static <R extends ImageRequest> void executorRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.executor,
                target.executor
        );
    }

    static <R extends ImageRequest> void modelIsDalle2(ImageConfigurationStage<R> nextStage) {
        assertEquals(
                nextStage.builder.model(),
                new DallE20().name()
        );
    }

    static <R extends ImageRequest> void modelIsDalle3(ImageConfigurationStage<R> nextStage) {
        assertEquals(
                nextStage.builder.model(),
                new DallE30().name()
        );
    }

    static <R extends ImageRequest> void modelRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.builder.model(),
                target.builder.model()
        );
    }

    static <R extends ImageRequest> void choicesRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.builder.n(),
                target.builder.n()
        );
    }

    static <R extends ImageRequest> void sizeRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.builder.size(),
                target.builder.size()
        );
    }

    static <R extends ImageRequest> void styleRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.builder.style(),
                target.builder.style()
        );
    }

    static <R extends ImageRequest> void qualityRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.builder.quality(),
                target.builder.quality()
        );
    }

    static <R extends ImageRequest> void promptRemainsUnchanged(
            ImageConfigurationStage<R> source,
            ImageConfigurationStage<R> target
    ) {
        assertEquals(
                source.builder.prompt(),
                target.builder.prompt()
        );
    }

    static <R extends ImageRequest> void choicesSetCorrectly(
            ImageConfigurationStage<R> nextStage,
            Integer choices
    ) {
        assertEquals(
                nextStage.builder.n(),
                choices
        );
    }

    static void createImageRequestIsPopulated(ImageConfigurationStage<CreateImageRequest> nextStage) {
        var request = nextStage.builder.specificRequestCreator()
                                       .apply(nextStage.builder);

        assertAll(
                () -> assertEquals(
                        request.model(),
                        nextStage.builder.model()
                ),
                () -> assertEquals(
                        request.prompt(),
                        nextStage.builder.prompt()
                ),
                () -> assertEquals(
                        request.size(),
                        nextStage.builder.size()
                ),
                () -> assertEquals(
                        request.quality(),
                        nextStage.builder.quality()
                ),
                () -> assertEquals(
                        request.style(),
                        nextStage.builder.style()
                ),
                () -> assertEquals(
                        request.responseFormat(),
                        nextStage.builder.responseFormat()
                ),
                () -> assertEquals(
                        request.n(),
                        nextStage.builder.n()
                ),
                () -> assertEquals(
                        request.user(),
                        nextStage.builder.user()
                )
        );
    }

    static void editImageRequestIsPopulated(ImageConfigurationStage<EditImageRequest> nextStage) {
        var request = nextStage.builder.specificRequestCreator()
                                       .apply(nextStage.builder);

        assertAll(
                () -> assertEquals(
                        request.image(),
                        nextStage.builder.image()
                ),
                () -> assertEquals(
                        request.mask(),
                        nextStage.builder.mask()
                ),
                () -> assertEquals(
                        request.model(),
                        nextStage.builder.model()
                ),
                () -> assertEquals(
                        request.prompt(),
                        nextStage.builder.prompt()
                ),
                () -> assertEquals(
                        request.size(),
                        nextStage.builder.size()
                ),
                () -> assertEquals(
                        request.responseFormat(),
                        nextStage.builder.responseFormat()
                ),
                () -> assertEquals(
                        request.n(),
                        nextStage.builder.n()
                ),
                () -> assertEquals(
                        request.user(),
                        nextStage.builder.user()
                )
        );
    }

    static void imageVariationRequestIsPopulated(ImageConfigurationStage<ImageVariationRequest> nextStage) {
        var request = nextStage.builder.specificRequestCreator()
                                       .apply(nextStage.builder);

        assertAll(
                () -> assertEquals(
                        request.image(),
                        nextStage.builder.image()
                ),
                () -> assertEquals(
                        request.model(),
                        nextStage.builder.model()
                ),
                () -> assertEquals(
                        request.prompt(),
                        nextStage.builder.prompt()
                ),
                () -> assertEquals(
                        request.size(),
                        nextStage.builder.size()
                ),
                () -> assertEquals(
                        request.responseFormat(),
                        nextStage.builder.responseFormat()
                ),
                () -> assertEquals(
                        request.n(),
                        nextStage.builder.n()
                ),
                () -> assertEquals(
                        request.user(),
                        nextStage.builder.user()
                )
        );
    }
}
