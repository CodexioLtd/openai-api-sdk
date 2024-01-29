package bg.codexio.ai.openai.api.http.message;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import okhttp3.Response;

import java.util.function.Supplier;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createErrorResponse;
import static bg.codexio.ai.openai.api.http.ExecutorTests.createOkResponse;

public class MessageHttpExecutorTestConstants {

    public static final String MESSAGE_TEST_URL = TEST_BASE_URL.concat(
            "/threads/%s/messages");
    public static final String MESSAGE_TEST_PATH_VARIABLE = "var";
    public static final String[] MESSAGE_TEST_PATH_VARIABLES = new String[]{
            "var"
    };
    public static final String MESSAGE_JSON_REQUEST_TEST_BODY =
            "{\"role" + "\":\"user\"" + ",\"content" + "\":\"test_content\"}";
    public static final MessageRequest MESSAGE_TEST_REQUEST =
            new MessageRequest(
            "user",
            "test_content",
            null,
            null
    );
    public static final MessageResponse MESSAGE_TEST_RESPONSE =
            new MessageResponse(
            "message_test_id",
            null,
            0,
            null,
            "user",
            null,
            null,
            null,
            null,
            null
    );
    public static final String MESSAGE_JSON_RESPONSE_TEST_BODY =
            "{\"id\":\"message_test_id\"," + "\"created_at\":0,\"role"
                    + "\":\"user\"}";
    public static final Supplier<Response> BASE_MESSAGE_TEST_JSON_RESPONSE =
            () -> createOkResponse(
            String.format(
                    MESSAGE_TEST_URL,
                    MESSAGE_TEST_PATH_VARIABLE
            ),
            MESSAGE_JSON_RESPONSE_TEST_BODY.getBytes(),
            "application/json"
    );
    public static final Supplier<Response> MESSAGE_ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            String.format(
                    MESSAGE_TEST_URL,
                    MESSAGE_TEST_PATH_VARIABLE
            ),
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );
    public static final String LIST_MESSAGES_JSON_RESPONSE_TEST_BODY =
            "{\"object\":\"list_test_messages\"}";
    public static final ListMessagesResponse LIST_MESSAGES_TEST_RESPONSE =
            new ListMessagesResponse(
            "list_test_messages",
            null,
            null,
            null,
            null
    );
    public static final Supplier<Response> BASE_LIST_MESSAGE_TEST_JSON_RESPONSE = () -> createOkResponse(
            String.format(
                    MESSAGE_TEST_URL,
                    (Object[]) MESSAGE_TEST_PATH_VARIABLES
            ),
            LIST_MESSAGES_JSON_RESPONSE_TEST_BODY.getBytes(),
            "application/json"
    );
    public static final Supplier<Response> LIST_MESSAGE_ERROR_JSON_RESPONSE =
            () -> createErrorResponse(
            String.format(
                    MESSAGE_TEST_URL,
                    (Object[]) MESSAGE_TEST_PATH_VARIABLES
            ),
            "{\"error\":{\"message\":\"Test Error\"}}".getBytes(),
            "application/json"
    );
}
