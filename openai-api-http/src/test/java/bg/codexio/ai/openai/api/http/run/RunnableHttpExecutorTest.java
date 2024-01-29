package bg.codexio.ai.openai.api.http.run;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.run.RunnableHttpExecutorTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class RunnableHttpExecutorTest {

    private OkHttpClient client;
    private RunnableHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecute_expectResponse() {
        ExecutorTests.testExecuteWithPathVariable_noError_shouldParseResponse(
                this.client,
                CREATE_RUN_TEST_URL,
                RUN_TEST_PATH_VARIABLE,
                RUN_TEST_JSON_REQUEST_BODY,
                BASE_RUN_JSON_TEST_RESPONSE.get(),
                RUN_TEST_REQUEST,
                RUN_TEST_RESPONSE,
                this.executor
        );
    }

    @Test
    void testExecute_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecuteWithPathVariable_withResponseError_shouldThrowException(
                this.client,
                CREATE_RUN_TEST_URL,
                RUN_TEST_PATH_VARIABLE,
                RUN_TEST_JSON_REQUEST_BODY,
                RUN_ERROR_JSON_RESPONSE.get(),
                RUN_TEST_REQUEST,
                this.executor
        );
    }

    @Test
    void testExecuteWithPathVariables_expectResponse() {
        ExecutorTests.testExecuteWithPathVariables_noError_shouldParseResponse(
                this.client,
                RETRIEVE_RUN_TEST_URL,
                BASE_RUN_JSON_TEST_RESPONSE.get(),
                RUN_TEST_RESPONSE,
                this.executor,
                RUN_TEST_PATH_VARIABLES
        );
    }

    @Test
    void testExecuteWithPathVariables_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecuteWithPathVariables_withResponseError_shouldThrowException(
                this.client,
                RETRIEVE_RUN_TEST_URL,
                RUN_ERROR_JSON_RESPONSE.get(),
                this.executor,
                RUN_TEST_PATH_VARIABLES
        );
    }

    @Test
    public void testCanStream_expectFalse() {
        assertFalse(this.executor.canStream(RUN_TEST_REQUEST));
    }

    private void initExecutor() {
        this.executor = new RunnableHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}
