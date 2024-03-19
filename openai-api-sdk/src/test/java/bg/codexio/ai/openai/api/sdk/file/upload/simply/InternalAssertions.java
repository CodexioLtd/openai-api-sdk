package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadingRuntimeSelectionStage;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.UPLOAD_FILE_HTTP_EXECUTOR;
import static org.mockito.Mockito.mockStatic;

public class InternalAssertions {
    static void mockFileUploadSimplified(
            Runnable execution,
            Runnable assertion
    ) {
        try (var fileUploadingMock = mockStatic(FileUploadSimplified.class)) {
            fileUploadingMock.when(FileUploadSimplified::toRuntimeSelection)
                             .thenReturn(new FileUploadingRuntimeSelectionStage(
                                     UPLOAD_FILE_HTTP_EXECUTOR,
                                     UploadFileRequest.builder()
                                                      .withPurpose(new AssistantPurpose().name())
                             ));

            execution.run();
            assertion.run();
        }
    }
}