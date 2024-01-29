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

public class RetrieveListMessagesHttpExecutorTest {

    private OkHttpClient client;
    private RetrieveListMessagesHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecuteWithPathVariables_expectResponse() {
        ExecutorTests.testExecuteWithPathVariables_noError_shouldParseResponse(
                this.client,
                MESSAGE_TEST_URL,
                BASE_LIST_MESSAGE_TEST_JSON_RESPONSE.get(),
                LIST_MESSAGES_TEST_RESPONSE,
                this.executor,
                MESSAGE_TEST_PATH_VARIABLES
        );
    }

    @Test
    void testExecuteWithPathVariables_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecuteWithPathVariables_withResponseError_shouldThrowException(
                this.client,
                MESSAGE_TEST_URL,
                LIST_MESSAGE_ERROR_JSON_RESPONSE.get(),
                this.executor,
                MESSAGE_TEST_PATH_VARIABLES
        );
    }

    @Test
    public void testCanStream_expectFalse() {
        assertFalse(this.executor.canStream(MESSAGE_TEST_REQUEST));
    }

    private void initExecutor() {
        this.executor = new RetrieveListMessagesHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}