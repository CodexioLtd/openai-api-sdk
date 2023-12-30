package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.DetailedAnalyze;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;
import bg.codexio.ai.openai.api.payload.vision.request.MessageContentHolder;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ImageDetailStageTest {

    private static final int CURRENT_INDEX = 3;
    private ImageDetailStage detailStage;

    private static String detailOnIndex(
            VisionConfigurationStage nextStage,
            int index
    ) {
        return (
                (ImageUrlMessageRequest) nextStage.requestContext.messages()
                                                                 .get(0)
                                                                 .content()
                                                                 .get(index)
        ).imageUrl()
         .detail();
    }

    private static void otherValuesRemainUnchanged(
            VisionConfigurationStage currentStage,
            VisionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> tokensRemainUnchanged(nextStage),
                () -> imageRemainsUnchanged(
                        nextStage,
                        CURRENT_INDEX
                ),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.detailStage = new ImageDetailStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
                             .withTokens(TEST_TOKENS)
                             .withMessages(new MessageContentHolder(List.of(
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             "first-url",
                                             null
                                     )),
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             "second-url",
                                             null
                                     )),
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             "third-url",
                                             null
                                     )),
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             TEST_IMAGE,
                                             null
                                     )),
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             "fifth-url",
                                             null
                                     ))
                             ))),
                CURRENT_INDEX
        );
    }

    @ParameterizedTest
    @EnumSource(DetailedAnalyze.class)
    public void testAnalyze_expectManualChoiceOnCorrectIndex(DetailedAnalyze analyzeType) {
        var nextStage = this.detailStage.analyze(analyzeType);

        assertAll(
                () -> assertEquals(
                        analyzeType.val(),
                        detailOnIndex(
                                nextStage,
                                CURRENT_INDEX
                        )
                ),
                () -> assertNotEquals(
                        analyzeType.val(),
                        detailOnIndex(
                                nextStage,
                                CURRENT_INDEX - 1
                        )
                ),
                () -> otherValuesRemainUnchanged(
                        this.detailStage,
                        nextStage
                )
        );
    }


}
