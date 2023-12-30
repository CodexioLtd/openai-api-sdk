package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronousApiTest {

    private SynchronousApi<CreateImageRequest> synchronousApi;

    @BeforeEach
    public void setUp() {
        this.synchronousApi = new SynchronousApi<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                CREATE_IMAGE_REQUEST_BUILDER
        );
    }

    @Test
    public void testGenerate_withPrompt_expectPromptInBuilder() {
        var nextStage = this.synchronousApi.generate(TEST_PROMPT);

        assertAll(
                () -> assertEquals(
                        TEST_PROMPT,
                        nextStage.builder.prompt()
                ),
                () -> executorRemainsUnchanged(
                        this.synchronousApi,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.synchronousApi,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.synchronousApi,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.synchronousApi,
                        nextStage
                ),
                () -> qualityRemainsUnchanged(
                        this.synchronousApi,
                        nextStage
                ),
                () -> styleRemainsUnchanged(
                        this.synchronousApi,
                        nextStage
                )
        );
    }
}
