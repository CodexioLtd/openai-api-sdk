package bg.codexio.ai.openai.api.http.file;

import bg.codexio.ai.openai.api.http.ExecutorTests;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static bg.codexio.ai.openai.api.http.file.FileHttpExecutorTestConstants.*;
import static org.mockito.Mockito.mock;

public class RetrieveFileContentHttpExecutorTest {

    private OkHttpClient client;
    private RetrieveFileContentHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    @Test
    void testExecuteWithPathVariables_expectResponse() throws IOException {
        ExecutorTests.testExecuteWithPathVariables_noError_shouldParseResponse(
                this.client,
                RETRIEVE_FILE_CONTENT_URL,
                RETRIEVE_FILE_CONTENT_JSON_RESPONSE.get(),
                new FileContentResponse(RETRIEVE_FILE_CONTENT_JSON_RESPONSE.get()
                                                                           .body()
                                                                           .bytes()),
                this.executor,
                RETRIEVE_FILE_PATH_VARIABLE
        );
    }

    @Test
    void testExecuteWithPathVariables_withError_expectOpenAIRespondedNot2xxException() {
        ExecutorTests.testExecuteWithPathVariables_withResponseError_shouldThrowException(
                this.client,
                RETRIEVE_FILE_CONTENT_URL,
                RETRIEVE_FILE_CONTENT_ERROR_JSON_RESPONSE.get(),
                this.executor,
                RETRIEVE_FILE_PATH_VARIABLE
        );
    }

    private void initExecutor() {
        this.executor = new RetrieveFileContentHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}