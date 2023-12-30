package bg.codexio.ai.openai.api.sdk.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class VariatingActionTypeStageTest {

    private VariatingActionTypeStage variatingActionTypeStage;

    @BeforeEach
    public void setUp() {
        this.variatingActionTypeStage =
                new VariatingActionTypeStage(IMAGE_VARIATION_HTTP_EXECUTOR);
    }

    @Test
    public void testAnother_executorShouldBeForVariations() {
        var nextStage =
                this.variatingActionTypeStage.another(new File(TEST_FILE_PATH));

        assertAll(
                () -> assertEquals(
                        nextStage.executor,
                        IMAGE_VARIATION_HTTP_EXECUTOR
                ),
                () -> assertInstanceOf(
                        PromptlessImagesRuntimeSelectionStage.class,
                        nextStage.runtimeSelector.apply(null)
                ),
                () -> imageVariationRequestIsPopulated(nextStage)
        );
    }
}
