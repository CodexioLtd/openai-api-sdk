package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileSimplifiedTest {

    @Test
    void testSimply_expectCorrectResponse() {
        try (var mockedFile = mockStatic(Files.class)) {
            this.initializeToTargetingStage(mockedFile);
            var id = FileSimplified.simply(FILE);
            assertNotNull(id);
        }
    }

    @Test
    void testSimplyRaw_expectCorrectResponse() {
        try (var mockedFile = mockStatic(Files.class)) {
            this.initializeToTargetingStage(mockedFile);
            var response = FileSimplified.simplyRaw(FILE);
            assertEquals(
                    new AssistantPurpose().name(),
                    response.purpose()
            );
        }
    }

    private void initializeToTargetingStage(MockedStatic<Files> mockedFile) {
        HttpBuilder<FileActionTypeStage> httpBuilderMock =
                mock(HttpBuilder.class);
        mockedFile.when(Files::defaults)
                  .thenReturn(httpBuilderMock);
        mockedFile.when(httpBuilderMock::and)
                  .thenReturn(new FileActionTypeStage(
                          UPLOAD_FILE_HTTP_EXECUTOR,
                          RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
                  ));
        when(UPLOAD_FILE_HTTP_EXECUTOR.execute(any())).thenAnswer(res -> FILE_RESPONSE);
    }
}