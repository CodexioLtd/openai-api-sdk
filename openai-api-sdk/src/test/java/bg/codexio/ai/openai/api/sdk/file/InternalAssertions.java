package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import org.mockito.MockedStatic;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class InternalAssertions {

    public static final File file = new File(
            "src/test/resources/fake-file" + ".txt");
    static final UploadFileHttpExecutor UPLOAD_FILE_HTTP_EXECUTOR =
            mock(UploadFileHttpExecutor.class);
    static final String ASSISTANT_PURPOSE_NAME = "assistants";
    public static final FileResponse FILE_RESPONSE = new FileResponse(
            "test_id",
            "test_object",
            0,
            0,
            file.getName(),
            ASSISTANT_PURPOSE_NAME
    );
    public static MockedStatic<FileSimplified> FILE_SIMPLIFIED =
            mockStatic(FileSimplified.class);
}
