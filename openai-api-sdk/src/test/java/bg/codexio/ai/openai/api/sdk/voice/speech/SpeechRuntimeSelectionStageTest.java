package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.TEST_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SpeechRuntimeSelectionStageTest {

    private SpeechRuntimeSelectionStage runtimeSelectionStage;

    @BeforeEach
    public void setUp() {
        this.runtimeSelectionStage = new SpeechRuntimeSelectionStage(
                TEST_EXECUTOR,
                SpeechRequest.builder()
        );
    }

    @Test
    public void testImmediate_shouldHaveSameReferences() {
        this.testExecutorAndBuilderAreTheSame(this.runtimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_shouldHaveSameReferences() {
        this.testExecutorAndBuilderAreTheSame(this.runtimeSelectionStage.async());
    }


    @Test
    public void testReactive_shouldHaveSameReferences() {
        this.testExecutorAndBuilderAreTheSame(this.runtimeSelectionStage.reactive());
    }

    private void testExecutorAndBuilderAreTheSame(SpeechConfigurationStage nextStage) {
        assertAll(
                () -> assertSame(
                        this.runtimeSelectionStage.executor,
                        nextStage.executor
                ),
                () -> assertSame(
                        this.runtimeSelectionStage.requestBuilder,
                        nextStage.requestBuilder
                )
        );
    }
}
