package bg.codexio.ai.openai.api.http.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;

import static bg.codexio.ai.openai.api.http.CommonTestConstantsUtils.TEST_BASE_URL;
import static org.mockito.Mockito.mock;

public class RetrieveFileContentHttpExecutorTest {

    private OkHttpClient client;
    private RetrieveFileContentHttpExecutor executor;

    @BeforeEach
    void setUp() {
        this.client = mock(OkHttpClient.class);
        this.initExecutor();
    }

    private void initExecutor() {
        this.executor = new RetrieveFileContentHttpExecutor(
                this.client,
                TEST_BASE_URL,
                new ObjectMapper()
        );
    }
}