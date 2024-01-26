package bg.codexio.ai.openai.api.http.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;

public class RunnableHttpExecutorTestConstants {

    public static final String CREATE_RUN_TEST_URL = TEST_BASE_URL.concat(
            "/threads/%s/runs");
    public static final String RUN_TEST_PATH_VARIABLE = "var";
    public static final String RUN_TEST_JSON_REQUEST_BODY =
            "{\"assistant_id" + "\":\"assistant_test_id\"}";
    public static final RunnableRequest RUN_TEST_REQUEST = new RunnableRequest(
            "assistant_test_id",
            null,
            null,
            null
    );

    public static final String RUN_TEST_JSON_RESPONSE_BODY =
            "{\"created_at\": 0,\n"
                    + "  \"assistant_id\": \"assistant_test_id\",\n"
                    + "  \"expires_at\": 0,\n" + "  \"started_at\": 0,\n"
                    + "  \"cancelled_at\": 0,\n" + "  \"failed_at\": 0,\n"
                    + "  \"completed_at\": 0,\n" + "  \"tools\": [],\n"
                    + "  \"file_ids\": [],\n" + "  \"metadata\": {}\n" + "}";
    public static final Supplier<Response> BASE_RUN_TEST_JSON_RESPONSE =
            () -> createOkResponse(
            String.format(
                    CREATE_RUN_TEST_URL,
                    RUN_TEST_PATH_VARIABLE
            ),
            RUN_TEST_JSON_RESPONSE_BODY.getBytes(),
            "application/json"
    );

    public static final RunnableResponse RUN_TEST_RESPONSE =
            new RunnableResponse(
            null,
            null,
            0,
            "assistant_test_id",
            null,
            null,
            null,
            null,
            0,
            0,
            0,
            0,
            0,
            null,
            null,
            new ArrayList<>(),
            new ArrayList<>(),
            new HashMap<>()
    );

    private RunnableHttpExecutorTestConstants() {
    }
}