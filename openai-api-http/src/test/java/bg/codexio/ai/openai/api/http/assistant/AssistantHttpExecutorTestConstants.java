package bg.codexio.ai.openai.api.http.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import okhttp3.Response;

import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;

public class AssistantHttpExecutorTestConstants {

    public static final String ASSISTANT_TEST_URL = TEST_BASE_URL.concat(
            "/assistants");
    public static final AssistantRequest ASSISTANT_TEST_REQUEST =
            new AssistantRequest(
            "ai_test_model",
            null,
            null,
            null,
            null,
            null,
            null
    );
    public static final String ASSISTANT_JSON_TEST_BODY_REQUEST =
            "{\"model\":\"ai" + "_test_model\"}";
    public static final AssistantResponse ASSISTANT_TEST_RESPONSE =
            new AssistantResponse(
            "assistant_test_id",
            null,
            0,
            null,
            null,
            "ai_test_model",
            null,
            null,
            null,
            null
    );
    public static final String ASSISTANT_JSON_TEST_BODY_RESPONSE =
            "{\"id\":\"assistant_test_id\","
                    + "\"created_at\":0,\"model\":\"ai_test_model\"}";
    public static final Supplier<Response> ASSISTANT_BASE_JSON_RESPONSE =
            () -> createOkResponse(
            ASSISTANT_TEST_URL,
            ASSISTANT_JSON_TEST_BODY_RESPONSE.getBytes(),
            "application/json"
    );
    public static final Supplier<Response> ASSISTANT_ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            ASSISTANT_TEST_URL,
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );

    private AssistantHttpExecutorTestConstants() {
    }
}