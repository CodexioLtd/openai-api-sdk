package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class FileDownloadingStageTest {

    private FileDownloadingStage<FileContentResponse> fileDownloadingStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingStage = new FileDownloadingStage<>(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                UploadFileRequest.builder(),
                FILE_TEST_ID,
                FILE_TEST_NAME
        );
    }

    @Test
    void testToFolder_expectCorrectResponse() throws IOException {
        var targetFolder = new File("folder");
        var response = new FileContentResponse(new byte[]{1, 2, 3});

        when(this.fileDownloadingStage.executor.executeWithPathVariables(any())).thenReturn(response);

        try (var downloadUtils = mockStatic(DownloadExecutor.class)) {
            var filePath = "var/files/result";
            downloadUtils.when(() -> DownloadExecutor.downloadTo(
                                 any(),
                                 any(),
                                 any()
                         ))
                         .thenReturn(new File(filePath));

            var result = this.fileDownloadingStage.toFolder(targetFolder);
            assertEquals(
                    filePath.replace(
                            "/",
                            File.separator
                    ),
                    result.getPath()
            );
        }
    }

}