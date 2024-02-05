package bg.codexio.ai.openai.api.http.thread;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutorTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class ModifyThreadHttpExecutorTest {

    private OkHttpClient client;
    private ModifyThreadHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecute_expectResponse() {
        ExecutorTests.testExecuteWithPathVariable_noError_shouldParseResponse(
                this.client,
                MODIFICATION_THREAD_URL,
                THREAD_MODIFICATION_TEST_PATH_VARIABLE,
                THREAD_MODIFICATION_JSON_BODY_TEST_REQUEST,
                THREAD_MODIFICATION_BASE_JSON_RESPONSE.get(),
                THREAD_MODIFICATION_TEST_REQUEST,
                THREAD_MODIFICATION_TEST_RESPONSE,
                this.executor
        );
    }

    @Test
    void testExecute_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecuteWithPathVariable_withResponseError_shouldThrowException(
                this.client,
                MODIFICATION_THREAD_URL,
                THREAD_MODIFICATION_TEST_PATH_VARIABLE,
                THREAD_MODIFICATION_JSON_BODY_TEST_REQUEST,
                THREAD_MODIFICATION_ERROR_JSON_RESPONSE.get(),
                THREAD_MODIFICATION_TEST_REQUEST,
                this.executor
        );
    }

    @Test
    public void testCanStream_expectFalse() {
        assertFalse(this.executor.canStream(THREAD_MODIFICATION_TEST_REQUEST));
    }

    private void initExecutor() {
        this.executor = new ModifyThreadHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}