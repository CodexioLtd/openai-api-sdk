package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.FILE;
import static org.mockito.Mockito.mock;

public class InternalAssertions {

    public static final UploadFileHttpExecutor UPLOAD_FILE_HTTP_EXECUTOR =
            mock(UploadFileHttpExecutor.class);
    static final String ASSISTANT_PURPOSE_NAME = "assistants";
    public static final FileResponse FILE_RESPONSE = new FileResponse(
            "test_id",
            "test_object",
            0,
            0,
            FILE.getName(),
            ASSISTANT_PURPOSE_NAME
    );
}
