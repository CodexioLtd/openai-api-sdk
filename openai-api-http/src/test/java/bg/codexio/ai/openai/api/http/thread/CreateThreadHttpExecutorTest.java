package bg.codexio.ai.openai.api.http.thread;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutorTestConstants.*;
import static org.mockito.Mockito.mock;

public class CreateThreadHttpExecutorTest {

    private OkHttpClient client;
    private CreateThreadHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    private void initExecutor() {
        this.executor = new CreateThreadHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }

    @Test
    void testExecute_expectResponse() {
        ExecutorTests.testExecute_noError_shouldParseResponse(
                this.client,
                CREATE_THREAD_URL,
                THREAD_CREATION_JSON_BODY_TEST_REQUEST,
                BASE_JSON_RESPONSE.get(),
                THREAD_CREATION_TEST_REQUEST,
                THREAD_TEST_RESPONSE,
                this.executor
        );
    }
}
