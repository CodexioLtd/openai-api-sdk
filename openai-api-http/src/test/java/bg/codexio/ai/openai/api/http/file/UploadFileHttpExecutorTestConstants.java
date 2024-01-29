package bg.codexio.ai.openai.api.http.file;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import okhttp3.Response;

import java.io.File;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;

public class UploadFileHttpExecutorTestConstants {

    public static final String UPLOAD_FILE_URL = TEST_BASE_URL.concat("/files");
    public static final UploadFileRequest UPLOAD_FILE_REQUEST_TEST =
            new UploadFileRequest(
            new File(UploadFileHttpExecutorTest.class.getClassLoader()
                                                     .getResource("fake-file.txt")
                                                     .getPath()),
            "assistants-test"
    );
    public static final String UPLOAD_FILE_REQUEST_BODY_TEST =
            "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"file\"; "
                    + "filename=\"fake-file.txt\"\n"
                    + "Content-Type: text/plain\n" + "\n" + "test\n" + "\n"
                    + "--test-boundary\n"
                    + "Content-Disposition: form-data; name=\"purpose\"\n"
                    + "\n" + "assistants-test\n" + "--test-boundary--";
    public static final FileResponse FILE_RESPONSE_TEST = new FileResponse(
            "test_file_id",
            "test_file_object",
            0,
            0,
            "fake-file.txt",
            "assistants-test"
    );
    public static final String FILE_RESPONSE_BODY_TEST =
            "{\"id\": \"test_file_id\",\n"
                    + "  \"object\": \"test_file_object\",\n"
                    + "  \"bytes\": 0,\n" + "  \"created_at\": 0,\n"
                    + "  \"filename\": \"fake-file.txt\",\n"
                    + "  \"purpose\": \"assistants-test\"\n" + "}";

    public static final Supplier<Response> BASE_JSON_RESPONSE =
            () -> createOkResponse(
            UPLOAD_FILE_URL,
            FILE_RESPONSE_BODY_TEST.getBytes(),
            "multipart/form-data"
    );
    public static final Supplier<Response> ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            UPLOAD_FILE_URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );

    private UploadFileHttpExecutorTestConstants() {
    }
}