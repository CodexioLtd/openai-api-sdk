package bg.codexio.ai.openai.api.http.file;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutorTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class UploadFileHttpExecutorTest {

    private OkHttpClient client;
    private UploadFileHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecute_expectResponse() {
        ExecutorTests.testExecute_noError_shouldParseResponse(
                this.client,
                UPLOAD_FILE_URL,
                UPLOAD_FILE_REQUEST_BODY_TEST,
                BASE_JSON_RESPONSE.get(),
                UPLOAD_FILE_REQUEST_TEST,
                FILE_RESPONSE_TEST,
                this.executor
        );
    }

    @Test
    void testExecute_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecute_withResponseError_shouldThrowException(
                this.client,
                UPLOAD_FILE_URL,
                UPLOAD_FILE_REQUEST_BODY_TEST,
                ERROR_JSON_RESPONSE.get(),
                UPLOAD_FILE_REQUEST_TEST,
                this.executor
        );
    }

    @Test
    public void testCanStream_expectFalse() {
        assertFalse(this.executor.canStream(UPLOAD_FILE_REQUEST_TEST));
    }

    private void initExecutor() {
        this.executor = new UploadFileHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );

        ExecutorTests.setMultipartBoundary(
                this.executor,
                "test-boundary"
        );
    }
}