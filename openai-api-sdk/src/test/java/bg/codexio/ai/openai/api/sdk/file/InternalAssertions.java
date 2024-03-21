package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InternalAssertions {
    static final String FILE_TEST_ID = "test_id";
    static final FileResult.Builder FILE_RESULT_BUILDER = FileResult.builder()
                                                                    .withId(FILE_TEST_ID)
                                                                    .withFileName("test_name");

    static final String FILE_TEST_PATH = "var/files/result";

    static final FileResult FILE_RESULT = new FileResult(
            FILE_TEST_ID,
            "test_name"
    );


    static void executeFileContentResponse() {
        when(RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR.retrieve(any())).thenReturn(new FileContentResponse(new byte[]{
                1, 2, 3
        }));
    }

    private InternalAssertions() {
    }
}
