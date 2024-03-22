package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockExecution;
import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.upload.simply.InternalAssertions.mockFileUploadSimplified;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class FileAsyncUploadSimplifiedTest {

    @Test
    public void testSimplyRaw_expectCorrectResponse() {
        var callback = prepareCallback(FileResponse.class);

        this.executeAsyncSimplified(() -> {
            FileAsyncUploadSimplified.simplyRaw(
                    FILE,
                    callback.callback()
            );

            assertNotNull(callback.data());
        });
    }

    @Test
    public void testSimply_expectCorrectResponse() {
        var callback = prepareCallback(String.class);

        this.executeAsyncSimplified(() -> {
            FileAsyncUploadSimplified.simply(
                    FILE,
                    callback.callback()
            );

            assertEquals(
                    FILE_RESPONSE.id(),
                    callback.data()
            );
        });
    }

    private <T> void executeAsyncSimplified(Runnable runnable) {
        mockFileUploadSimplified(
                () -> {
                    try {
                        mockExecution(
                                UPLOAD_FILE_HTTP_EXECUTOR,
                                FILE_RESPONSE,
                                OBJECT_MAPPER.writeValueAsString(FILE_RESPONSE)
                        );
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                },
                runnable
        );
    }
}