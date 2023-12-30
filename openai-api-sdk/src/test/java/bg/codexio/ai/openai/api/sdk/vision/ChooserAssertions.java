package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.TEST_IMAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class ChooserAssertions {

    private static String urlOnIndex(
            VisionConfigurationStage nextStage,
            int index
    ) {
        try {
            return (
                    (ImageUrlMessageRequest) nextStage.requestContext.messages()
                                                                     .get(0)
                                                                     .content()
                                                                     .get(index)
            ).imageUrl()
             .url();
        } catch (Exception ignored) {
            return "(empty)";
        }
    }

    public static void testFileImageOnIndex(
            int index,
            Function<File, VisionConfigurationStage> explanation,
            VisionConfigurationStage currentStage,
            BiConsumer<VisionConfigurationStage, VisionConfigurationStage> otherValuesTester
    ) {
        var image = new File("test/file.png");
        try (var requestUtils = mockStatic(ImageUrlRequest.class)) {
            requestUtils.when(() -> ImageUrlRequest.fromLocalFile(eq(image)))
                        .thenReturn(new ImageUrlRequest(
                                TEST_IMAGE,
                                null
                        ));
            var nextStage = explanation.apply(image);

            assertAll(
                    () -> assertEquals(
                            TEST_IMAGE,
                            urlOnIndex(
                                    nextStage,
                                    index
                            )
                    ),
                    () -> assertNotEquals(
                            TEST_IMAGE,
                            urlOnIndex(
                                    nextStage,
                                    index - 1
                            )
                    ),
                    () -> otherValuesTester.accept(
                            currentStage,
                            nextStage
                    )
            );
        }
    }

    public static void testUrlImageOnIndex(
            int index,
            Function<String, VisionConfigurationStage> explanation,
            VisionConfigurationStage currentStage,
            BiConsumer<VisionConfigurationStage, VisionConfigurationStage> otherValuesTester
    ) {
        var nextStage = explanation.apply("some-url");

        assertAll(
                () -> assertEquals(
                        "some-url",
                        urlOnIndex(
                                nextStage,
                                index
                        )
                ),
                () -> assertNotEquals(
                        "some-url",
                        urlOnIndex(
                                nextStage,
                                index - 1
                        )
                ),
                () -> otherValuesTester.accept(
                        currentStage,
                        nextStage
                )
        );
    }
}
