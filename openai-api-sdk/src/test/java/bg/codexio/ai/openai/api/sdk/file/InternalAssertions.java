package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InternalAssertions {
    static final String ASSISTANT_PURPOSE_NAME = "assistants";
    static final String FILE_TEST_ID = "test_id";
    static final String FILE_TEST_NAME = "test_name";
    static final FileResult.Builder FILE_RESULT_BUILDER = FileResult.builder()
                                                                    .withId(FILE_TEST_ID)
                                                                    .withFileName(FILE_TEST_NAME);
    static final FileResult FILE_RESULT = new FileResult(
            FILE_TEST_ID,
            FILE_TEST_NAME
    );
    static final FileContentResponse FILE_CONTENT_RESPONSE =
            new FileContentResponse(new byte[]{
            1, 2, 3
    });
    public static final FileResponse FILE_RESPONSE = new FileResponse(
            FILE_TEST_ID,
            "test_object",
            0,
            0,
            FILE_TEST_NAME,
            ASSISTANT_PURPOSE_NAME
    );
    static final String FILE_TEST_PATH = "var/files/result";
    static final File TARGET_TEST_FOLDER = new File("folder");

    static void executeFileContentResponse() {
        when(RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR.executeWithPathVariables(any())).thenReturn(FILE_CONTENT_RESPONSE);
    }

    private InternalAssertions() {
    }
}
