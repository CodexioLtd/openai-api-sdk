package bg.codexio.ai.openai.api.http.message;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.message.MessageHttpExecutorTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class MessageHttpExecutorTest {

    private OkHttpClient client;
    private MessageHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecuteWithPathVariable_expectResponse() {
        ExecutorTests.testExecuteWithPathVariable_noError_shouldParseResponse(
                this.client,
                MESSAGE_TEST_URL,
                MESSAGE_TEST_PATH_VARIABLE,
                MESSAGE_JSON_REQUEST_TEST_BODY,
                BASE_MESSAGE_TEST_JSON_RESPONSE.get(),
                MESSAGE_TEST_REQUEST,
                MESSAGE_TEST_RESPONSE,
                this.executor
        );
    }

    @Test
    void testExecuteWithPathVariable_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecuteWithPathVariable_withResponseError_shouldThrowException(
                this.client,
                MESSAGE_TEST_URL,
                MESSAGE_TEST_PATH_VARIABLE,
                MESSAGE_JSON_REQUEST_TEST_BODY,
                MESSAGE_ERROR_JSON_RESPONSE.get(),
                MESSAGE_TEST_REQUEST,
                this.executor
        );
    }

    @Test
    public void testCanStream_expectFalse() {
        assertFalse(this.executor.canStream(MESSAGE_TEST_REQUEST));
    }

    private void initExecutor() {
        this.executor = new MessageHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}
