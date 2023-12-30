package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ImageChooserStageTest {

    private ImageChooserStage chooserStage;

    private static void otherValuesRemainUnchanged(
            VisionConfigurationStage currentStage,
            VisionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> tokensRemainUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.chooserStage = new ImageChooserStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
                             .withTokens(TEST_TOKENS)
        );
    }

    @Test
    public void testExplain_withFile_expectFilesBase64() {
        ChooserAssertions.testFileImageOnIndex(
                0,
                this.chooserStage::explain,
                this.chooserStage,
                ImageChooserStageTest::otherValuesRemainUnchanged
        );
    }

    @Test
    public void testExplain_withUrl_expectGivenUrl() {
        ChooserAssertions.testUrlImageOnIndex(
                0,
                this.chooserStage::explain,
                this.chooserStage,
                ImageChooserStageTest::otherValuesRemainUnchanged
        );
    }

}
