package bg.codexio.ai.openai.api.http.assistant;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutorTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class AssistantHttpExecutorTest {

    private OkHttpClient client;
    private AssistantHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecute_expectResponse() {
        ExecutorTests.testExecute_noError_shouldParseResponse(
                this.client,
                ASSISTANT_TEST_URL,
                ASSISTANT_JSON_TEST_BODY_REQUEST,
                ASSISTANT_BASE_JSON_RESPONSE.get(),
                ASSISTANT_TEST_REQUEST,
                ASSISTANT_TEST_RESPONSE,
                this.executor
        );
    }

    @Test
    void testExecute_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecute_withResponseError_shouldThrowException(
                this.client,
                ASSISTANT_TEST_URL,
                ASSISTANT_JSON_TEST_BODY_REQUEST,
                ASSISTANT_ERROR_JSON_RESPONSE.get(),
                ASSISTANT_TEST_REQUEST,
                this.executor
        );
    }

    @Test
    public void testCanStream_expectFalse() {
        assertFalse(this.executor.canStream(ASSISTANT_TEST_REQUEST));
    }

    private void initExecutor() {
        this.executor = new AssistantHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}