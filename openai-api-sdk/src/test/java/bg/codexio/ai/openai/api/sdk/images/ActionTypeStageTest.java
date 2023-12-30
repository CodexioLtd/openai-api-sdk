package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTypeStageTest<R extends ImageRequest> {

    private ActionTypeStage<R> actionTypeStage;

    @BeforeEach
    public void setUp() {
        this.actionTypeStage = new ActionTypeStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                EDIT_IMAGE_HTTP_EXECUTOR,
                IMAGE_VARIATION_HTTP_EXECUTOR
        );
    }

    @Test
    public void testCreating_expectCreateExecutor() {
        var nextStage = this.actionTypeStage.creating();

        assertAll(
                () -> assertEquals(
                        nextStage.executor,
                        CREATE_IMAGE_HTTP_EXECUTOR
                ),
                () -> createImageRequestIsPopulated(nextStage)
        );
    }

    @Test
    public void testEditing_expectEditExecutor() {
        var file = new File(TEST_FILE_PATH);
        var nextStage = this.actionTypeStage.editing(file);

        assertAll(
                () -> assertEquals(
                        nextStage.executor,
                        EDIT_IMAGE_HTTP_EXECUTOR
                ),
                () -> assertEquals(
                        file,
                        nextStage.builder.image()
                ),
                () -> editImageRequestIsPopulated(nextStage)
        );
    }

    @Test
    public void testAnother_expectImageVariationExecutor() {
        var file = new File(TEST_FILE_PATH);
        var nextStage = this.actionTypeStage.another(file);

        assertAll(
                () -> assertEquals(
                        nextStage.executor,
                        IMAGE_VARIATION_HTTP_EXECUTOR
                ),
                () -> assertEquals(
                        file,
                        nextStage.builder.image()
                ),
                () -> modelIsDalle2(nextStage),
                () -> imageVariationRequestIsPopulated(nextStage)
        );
    }
}
