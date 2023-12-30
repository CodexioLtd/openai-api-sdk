package bg.codexio.ai.openai.api.sdk.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.CREATE_IMAGE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.createImageRequestIsPopulated;
import static org.junit.jupiter.api.Assertions.*;

public class CreatingActionTypeStageTest {

    private CreatingActionTypeStage creatingActionTypeStage;

    @BeforeEach
    public void setUp() {
        this.creatingActionTypeStage =
                new CreatingActionTypeStage(CREATE_IMAGE_HTTP_EXECUTOR);
    }

    @Test
    public void testCreating_expectCreateExecutor() {
        var nextStage = this.creatingActionTypeStage.creating();

        assertAll(
                () -> assertEquals(
                        nextStage.executor,
                        CREATE_IMAGE_HTTP_EXECUTOR
                ),
                () -> assertInstanceOf(
                        PromptfulImagesRuntimeSelectionStage.class,
                        nextStage.runtimeSelector.apply(null)
                ),
                () -> createImageRequestIsPopulated(nextStage)
        );
    }
}
