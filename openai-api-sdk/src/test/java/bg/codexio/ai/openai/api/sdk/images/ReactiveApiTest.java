package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactiveApiTest {

    private ReactiveApi<CreateImageRequest> reactiveApi;

    @BeforeEach
    public void setUp() {
        this.reactiveApi = new ReactiveApi<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                CREATE_IMAGE_REQUEST_BUILDER
        );
    }

    @Test
    public void testGenerate_withPrompt_expectPrompt() {
        var nextStage = this.reactiveApi.generate(TEST_PROMPT);

        assertAll(
                () -> assertEquals(
                        TEST_PROMPT,
                        nextStage.builder.prompt()
                ),
                () -> executorRemainsUnchanged(
                        this.reactiveApi,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.reactiveApi,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.reactiveApi,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.reactiveApi,
                        nextStage
                ),
                () -> qualityRemainsUnchanged(
                        this.reactiveApi,
                        nextStage
                ),
                () -> styleRemainsUnchanged(
                        this.reactiveApi,
                        nextStage
                )
        );
    }
}
