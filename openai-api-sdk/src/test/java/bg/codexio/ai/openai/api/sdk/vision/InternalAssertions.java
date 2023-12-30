package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.v40.GPT40VisionPreviewModel;
import bg.codexio.ai.openai.api.payload.vision.DetailedAnalyze;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.QuestionVisionRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public final class InternalAssertions {

    static final ModelTypeAbstract TEST_MODEL = new GPT40VisionPreviewModel();
    static final Integer TEST_TOKENS = 300;
    static final String TEST_IMAGE = "data:image/png;base64,test-str";
    static final String TEST_ANALYZE = DetailedAnalyze.HIGH.val();
    static final String TEST_DESCRIPTION = "Test";
    static final VisionHttpExecutor TEST_EXECUTOR =
            mock(VisionHttpExecutor.class);

    private InternalAssertions() {

    }


    static void modelRemainsUnchanged(VisionConfigurationStage nextStage) {
        assertEquals(
                TEST_MODEL.name(),
                nextStage.requestContext.model()
        );
    }

    static void tokensRemainUnchanged(VisionConfigurationStage nextStage) {
        assertEquals(
                TEST_TOKENS,
                nextStage.requestContext.maxTokens()
        );
    }

    static void imageRemainsUnchanged(
            VisionConfigurationStage nextStage,
            int index
    ) {
        assertEquals(
                TEST_IMAGE,
                (
                        (ImageUrlMessageRequest) nextStage.requestContext.messages()
                                                                         .get(0)
                                                                         .content()
                                                                         .get(index)
                ).imageUrl()
                 .url()
        );
    }

    static void detailsRemainsUnchanged(
            VisionConfigurationStage nextStage,
            int index
    ) {
        assertEquals(
                TEST_ANALYZE,
                (
                        (ImageUrlMessageRequest) nextStage.requestContext.messages()
                                                                         .get(0)
                                                                         .content()
                                                                         .get(index)
                ).imageUrl()
                 .detail()
        );
    }

    static void promptRemainsUnchanged(
            VisionConfigurationStage nextStage,
            int index
    ) {
        assertEquals(
                TEST_DESCRIPTION,
                (
                        (QuestionVisionRequest) nextStage.requestContext.messages()
                                                                        .get(0)
                                                                        .content()
                                                                        .get(index)
                ).text()
        );
    }

    static void executorRemainsUnchanged(
            VisionConfigurationStage source,
            VisionConfigurationStage target
    ) {
        assertEquals(
                source.executor,
                target.executor
        );
    }
}
