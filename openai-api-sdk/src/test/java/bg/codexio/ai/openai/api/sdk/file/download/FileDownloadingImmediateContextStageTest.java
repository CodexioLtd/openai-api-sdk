package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_TEST_NAME;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.FILE_TEST_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class FileDownloadingImmediateContextStageTest {

    private FileDownloadingImmediateContextStage fileDownloadingStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingStage = new FileDownloadingImmediateContextStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FileDownloadingMeta.builder()
                                   .withFileId(FILE_TEST_ID)
                                   .withFileName(FILE_TEST_NAME)
        );
    }

    @Test
    void testToFolder_expectCorrectResponse() throws IOException {
        var response = new FileContentResponse(new byte[]{1, 2, 3});

        when(RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR.executeWithPathVariables(any())).thenReturn(response);

        try (var downloadUtils = mockStatic(DownloadExecutor.class)) {
            downloadUtils.when(() -> DownloadExecutor.downloadTo(
                                 any(),
                                 any(),
                                 any()
                         ))
                         .thenReturn(new File(FILE_TEST_PATH));

            var result = this.fileDownloadingStage.toFolder(FILE);
            assertEquals(
                    FILE_TEST_PATH.replace(
                            "/",
                            File.separator
                    ),
                    result.getPath()
            );
        }
    }

}