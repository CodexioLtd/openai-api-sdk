package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.models.v40.GPT40VisionPreviewModel;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.vision.DetailedAnalyze;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class SimplifiedStageTest {

    private SimplifiedStage simplifiedStage;

    private static void assertHydratedRequest(VisionConfigurationStage nextStage) {
        assertAll(
                () -> assertEquals(
                        new GPT40VisionPreviewModel().name(),
                        nextStage.requestContext.model()
                ),
                () -> assertEquals(
                        300,
                        nextStage.requestContext.maxTokens()
                ),
                () -> assertEquals(
                        new ImageUrlRequest(
                                "test-url",
                                DetailedAnalyze.HIGH.val()
                        ),
                        (
                                (ImageUrlMessageRequest) nextStage.requestContext.messages()
                                                                                 .get(0)
                                                                                 .content()
                                                                                 .get(0)
                        ).imageUrl()
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.simplifiedStage =
                new SimplifiedStage(FromDeveloper.doPass(new ApiCredentials(
                        "test-key")));
    }

    @Test
    public void testExplain_withFile_expectHydratedRequest() {
        var image = new File("test/image.png");
        try (var requestUtils = mockStatic(ImageUrlRequest.class)) {
            requestUtils.when(() -> ImageUrlRequest.fromLocalFile(eq(image)))
                        .thenReturn(new ImageUrlRequest(
                                "test-url",
                                null
                        ));

            var nextStage = this.simplifiedStage.explain(image);

            assertHydratedRequest(nextStage);
        }
    }

    @Test
    public void testExplain_withUrlOrBase64_expectHydratedRequest() {
        var nextStage = this.simplifiedStage.explain("test-url");

        assertHydratedRequest(nextStage);
    }
}
