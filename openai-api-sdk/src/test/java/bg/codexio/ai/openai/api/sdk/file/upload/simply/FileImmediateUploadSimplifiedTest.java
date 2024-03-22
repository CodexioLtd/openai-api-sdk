package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.upload.simply.InternalAssertions.mockFileUploadSimplified;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileImmediateUploadSimplifiedTest {

    @Test
    void testSimplyRaw_expectCorrectResponse() {
        mockFileUploadSimplified(
                this::mockImmediateExecution,
                () -> assertEquals(
                        new AssistantPurpose().name(),
                        FileImmediateUploadSimplified.simplyRaw(FILE)
                                                     .purpose()
                )
        );
    }

    @Test
    void testSimply_expectCorrectResponse() {
        mockFileUploadSimplified(
                this::mockImmediateExecution,
                () -> assertNotNull(FileImmediateUploadSimplified.simply(FILE))
        );
    }

    private void mockImmediateExecution() {
        when(UPLOAD_FILE_HTTP_EXECUTOR.immediate()
                                      .execute(any())).thenAnswer(res -> FILE_RESPONSE);
    }
}