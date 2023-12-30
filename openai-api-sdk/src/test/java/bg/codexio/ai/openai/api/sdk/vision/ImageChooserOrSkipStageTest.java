package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;
import bg.codexio.ai.openai.api.payload.vision.request.MessageContentHolder;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ImageChooserOrSkipStageTest {

    private static final int CURRENT_INDEX = 3;

    private ImageChooserOrSkipStage chooserStage;

    private static String urlOnIndex(
            VisionConfigurationStage nextStage,
            int index
    ) {
        return (
                (ImageUrlMessageRequest) nextStage.requestContext.messages()
                                                                 .get(0)
                                                                 .content()
                                                                 .get(index)
        ).imageUrl()
         .url();
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
                () -> detailsRemainsUnchanged(
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
        this.chooserStage = new ImageChooserOrSkipStage(
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
                                             TEST_ANALYZE
                                     )),
                                     new ImageUrlMessageRequest(new ImageUrlRequest(
                                             "fifth-url",
                                             null
                                     ))
                             ))),
                CURRENT_INDEX
        );
    }

    @Test
    public void testExplainAnother_withFile_expectFilesBase64OnLastPosition() {
        ChooserAssertions.testFileImageOnIndex(
                this.chooserStage.requestContext.messages()
                                                .get(0)
                                                .content()
                                                .size(),
                this.chooserStage::explainAnother,
                this.chooserStage,
                ImageChooserOrSkipStageTest::otherValuesRemainUnchanged
        );
    }

    @Test
    public void testExplainAnother_withUrl_expectGivenUrlOnLastPosition() {
        ChooserAssertions.testUrlImageOnIndex(
                this.chooserStage.requestContext.messages()
                                                .get(0)
                                                .content()
                                                .size(),
                this.chooserStage::explainAnother,
                this.chooserStage,
                ImageChooserOrSkipStageTest::otherValuesRemainUnchanged
        );
    }

    @Test
    public void testAndRespond_expectValuesUnchanged() {
        var nextStage = this.chooserStage.andRespond();

        otherValuesRemainUnchanged(
                this.chooserStage,
                nextStage
        );
    }
}
