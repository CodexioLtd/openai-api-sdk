package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.TEST_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TranscriptionRuntimeSelectionStageTest {

    private TranscriptionRuntimeSelectionStage runtimeSelectionStage;

    @BeforeEach
    public void setUp() {
        this.runtimeSelectionStage = new TranscriptionRuntimeSelectionStage(
                TEST_EXECUTOR,
                TranscriptionRequest.builder()
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

    private void testExecutorAndBuilderAreTheSame(TranscriptionConfigurationStage nextStage) {
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
