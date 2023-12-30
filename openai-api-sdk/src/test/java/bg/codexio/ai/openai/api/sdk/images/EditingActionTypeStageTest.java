package bg.codexio.ai.openai.api.sdk.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class EditingActionTypeStageTest {

    private EditingActionTypeStage editingActionTypeStage;

    @BeforeEach
    public void setUp() {
        this.editingActionTypeStage =
                new EditingActionTypeStage(EDIT_IMAGE_HTTP_EXECUTOR);
    }

    @Test
    public void testEditing_expectEditExecutor() {
        var nextStage =
                this.editingActionTypeStage.editing(new File(TEST_FILE_PATH));

        assertAll(
                () -> assertEquals(
                        nextStage.executor,
                        EDIT_IMAGE_HTTP_EXECUTOR
                ),
                () -> assertInstanceOf(
                        PromptfulImagesRuntimeSelectionStage.class,
                        nextStage.runtimeSelector.apply(null)
                ),
                () -> editImageRequestIsPopulated(nextStage)
        );
    }
}
