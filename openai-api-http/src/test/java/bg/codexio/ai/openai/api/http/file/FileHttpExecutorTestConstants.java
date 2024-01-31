package bg.codexio.ai.openai.api.http.file;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import okhttp3.Response;

import java.io.File;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;

public class FileHttpExecutorTestConstants {

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
    public static final String RETRIEVE_FILE_CONTENT_URL =
            TEST_BASE_URL.concat("/files/%s/content");
    public static final String[] RETRIEVE_FILE_PATH_VARIABLE = new String[]{
            "var"
    };
    public static final FileContentResponse FILE_CONTENT_TEST_RESPONSE =
            new FileContentResponse(new byte[]{
            1, 2, 3
    });
    public static final Supplier<Response> RETRIEVE_FILE_CONTENT_JSON_RESPONSE = () -> createOkResponse(
            String.format(
                    RETRIEVE_FILE_CONTENT_URL,
                    (Object[]) RETRIEVE_FILE_PATH_VARIABLE
            ),
            new byte[]{1, 2, 3},
            "multipart/form-data"
    );
    public static final Supplier<Response> RETRIEVE_FILE_CONTENT_ERROR_JSON_RESPONSE = () -> createErrorResponse(
            String.format(
                    RETRIEVE_FILE_CONTENT_URL,
                    (Object[]) RETRIEVE_FILE_PATH_VARIABLE
            ),
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );

    private FileHttpExecutorTestConstants() {
    }
}