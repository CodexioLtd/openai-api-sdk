package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncApiTest {

    private AsyncApi<CreateImageRequest> asyncApi;

    @BeforeEach
    public void setUp() {
        this.asyncApi = new AsyncApi<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                CREATE_IMAGE_REQUEST_BUILDER
        );
    }

    @Test
    public void testGenerate_withUserPrompt_expectPromptInBuilder() {
        var nextStage = this.asyncApi.generate(TEST_PROMPT);

        assertAll(
                () -> assertEquals(
                        TEST_PROMPT,
                        nextStage.builder.prompt()
                ),
                () -> executorRemainsUnchanged(
                        this.asyncApi,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.asyncApi,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.asyncApi,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.asyncApi,
                        nextStage
                ),
                () -> qualityRemainsUnchanged(
                        this.asyncApi,
                        nextStage
                ),
                () -> styleRemainsUnchanged(
                        this.asyncApi,
                        nextStage
                )
        );
    }
}
