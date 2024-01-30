package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import static org.mockito.Mockito.mock;

public class InternalAssertions {

    static final UploadFileHttpExecutor UPLOAD_FILE_HTTP_EXECUTOR =
            mock(UploadFileHttpExecutor.class);
    static final RetrieveFileContentHttpExecutor RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR = mock(RetrieveFileContentHttpExecutor.class);
    static final String ASSISTANT_PURPOSE_NAME = "assistants";
    static final String FILE_TEST_ID = "test_id";
    static final String FILE_TEST_NAME = "test_name";
    public static final FileResponse FILE_RESPONSE = new FileResponse(
            FILE_TEST_ID,
            "test_object",
            0,
            0,
            FILE_TEST_NAME,
            ASSISTANT_PURPOSE_NAME
    );
}
