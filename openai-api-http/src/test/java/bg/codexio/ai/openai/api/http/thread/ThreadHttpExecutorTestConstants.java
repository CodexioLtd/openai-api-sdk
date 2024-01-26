package bg.codexio.ai.openai.api.http.thread;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;

public class ThreadHttpExecutorTestConstants {
    public static final String CREATE_THREAD_URL = TEST_BASE_URL.concat(
            "/threads");
    public static final ThreadCreationRequest THREAD_CREATION_TEST_REQUEST =
            new ThreadCreationRequest(
            new ArrayList<>(),
            new HashMap<>()
    );
    public static final String THREAD_CREATION_JSON_BODY_TEST_REQUEST =
            "{\"messages\":" + "[]," + "\"metadata\":" + "{}}";
    public static final ThreadResponse THREAD_TEST_RESPONSE =
            new ThreadResponse(
            "thread_test_id",
            null,
            0,
            new HashMap<>()
    );
    public static final String THREAD_JSON_BODY_TEST_RESPONSE =
            "{\"id\": \"thread_test_id\",\n" + "\"object\": null,\n"
                    + "  \"created_at\": 0,\n" + "  \"metadata\": {}\n" + "}\n";
    public static final Supplier<Response> THREAD_CREATION_BASE_JSON_RESPONSE =
            () -> createOkResponse(
            CREATE_THREAD_URL,
            THREAD_JSON_BODY_TEST_RESPONSE.getBytes(),
            "multipart/form-data"
    );
    public static final Supplier<Response> THREAD_ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            CREATE_THREAD_URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );
    public static final String MODIFICATION_THREAD_URL =
            TEST_BASE_URL.concat("/threads/%s");
    public static final String THREAD_MODIFICATION_TEST_PATH_VARIABLE =
            "thread_id";
    public static final ThreadModificationRequest THREAD_MODIFICATION_TEST_REQUEST = new ThreadModificationRequest(Map.of(
            "test_key",
            "test_value"
    ));
    public static final String THREAD_MODIFICATION_JSON_BODY_TEST_REQUEST =
            "{\"metadata\":" + "{\"test_key\":\"test_value\"}}";
    public static final ThreadResponse THREAD_MODIFICATION_TEST_RESPONSE =
            new ThreadResponse(
            null,
            null,
            null,
            Map.of(
                    "test_key",
                    "test_value"
            )
    );
    public static final String THREAD_MODIFICATION_JSON_BODY_TEST_RESPONSE =
            "{\"metadata\":" + "{\"test_key\":\"test_value\"}}";
    public static final Supplier<Response> THREAD_MODIFICATION_BASE_JSON_RESPONSE = () -> createOkResponse(
            MODIFICATION_THREAD_URL,
            THREAD_MODIFICATION_JSON_BODY_TEST_RESPONSE.getBytes(),
            "multipart/form-data"
    );
    public static final Supplier<Response> THREAD_MODIFICATION_ERROR_JSON_RESPONSE = () -> createErrorResponse(
            MODIFICATION_THREAD_URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );
}
