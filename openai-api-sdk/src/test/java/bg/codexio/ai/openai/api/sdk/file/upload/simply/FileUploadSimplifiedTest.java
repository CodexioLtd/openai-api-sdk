package bg.codexio.ai.openai.api.sdk.file.upload.simply;


import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.file.FileActionTypeStage;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadingRuntimeSelectionStage;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.UPLOAD_FILE_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class FileUploadSimplifiedTest {

    @Test
    public void testToRuntimeSelection() {
        try (var mockedFile = mockStatic(Files.class)) {
            HttpBuilder<FileActionTypeStage> httpBuilderMock =
                    mock(HttpBuilder.class);
            mockedFile.when(Files::defaults)
                      .thenReturn(httpBuilderMock);
            mockedFile.when(httpBuilderMock::and)
                      .thenReturn(new FileActionTypeStage(
                              UPLOAD_FILE_HTTP_EXECUTOR,
                              RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
                      ));

            assertInstanceOf(
                    FileUploadingRuntimeSelectionStage.class,
                    FileUploadSimplified.toRuntimeSelection()
            );
        }
    }
}
