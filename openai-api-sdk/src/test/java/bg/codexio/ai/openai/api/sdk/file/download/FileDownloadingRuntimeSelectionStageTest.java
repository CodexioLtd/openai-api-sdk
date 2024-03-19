package bg.codexio.ai.openai.api.sdk.file.download;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_DOWNLOADING_META_WITHOUT_FOLDER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileDownloadingRuntimeSelectionStageTest {

    private FileDownloadingRuntimeSelectionStage fileDownloadingRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingRuntimeSelectionStage =
                new FileDownloadingRuntimeSelectionStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FILE_DOWNLOADING_META_WITHOUT_FOLDER
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        assertNotNull(this.fileDownloadingRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        assertNotNull(this.fileDownloadingRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        assertNotNull(this.fileDownloadingRuntimeSelectionStage.reactive());
    }
}
