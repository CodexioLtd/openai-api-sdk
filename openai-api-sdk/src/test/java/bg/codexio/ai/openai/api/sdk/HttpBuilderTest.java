package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.HttpTimeout;
import bg.codexio.ai.openai.api.http.HttpTimeouts;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class HttpBuilderTest {

    private static final HttpExecutorContext CTX =
            new HttpExecutorContext(new ApiCredentials("test-key"));

    private HttpBuilder<NextStage> httpBuilder;

    @BeforeEach
    public void setUp() {
        this.httpBuilder = new HttpBuilder<>(CTX, NextStage::new);
    }

    @Test
    public void testAnd_shouldProvideNextStageWithCtxAndMapper() {
        var nextStage = this.httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.getMapper()),
                () -> assertEquals(CTX, nextStage.getCtx())
        );
    }

    @Test
    public void testUnderstand_shouldProvideNextStageWithCtxAndMapper() {
        var mapper = new ObjectMapper();
        var nextStage = this.httpBuilder.understanding(mapper);

        assertAll(
                () -> assertEquals(mapper, nextStage.getMapper()),
                () -> assertEquals(CTX, nextStage.getCtx())
        );
    }

    @Test
    public void testWithCallTimeout_expectNewTimeout() {
        var nextStage = this.httpBuilder.withCallTimeout(50, TimeUnit.MICROSECONDS).and();

        assertAll(
                () -> assertNotNull(nextStage.getMapper()),
                () -> assertEquals(
                        new HttpExecutorContext(
                                new ApiCredentials("test-key"),
                                new HttpTimeouts(
                                        new HttpTimeout(50, TimeUnit.MICROSECONDS),
                                        new HttpTimeout(3, TimeUnit.MINUTES),
                                        new HttpTimeout(3, TimeUnit.MINUTES)
                                )
                        ),
                        nextStage.getCtx()
                )
        );
    }

    @Test
    public void testWithConnectTimeout_expectNewTimeout() {
        var nextStage = this.httpBuilder.withConnectTimeout(2, TimeUnit.HOURS).and();

        assertAll(
                () -> assertNotNull(nextStage.getMapper()),
                () -> assertEquals(
                        new HttpExecutorContext(
                                new ApiCredentials("test-key"),
                                new HttpTimeouts(
                                        new HttpTimeout(3, TimeUnit.MINUTES),
                                        new HttpTimeout(2, TimeUnit.HOURS),
                                        new HttpTimeout(3, TimeUnit.MINUTES)
                                )
                        ),
                        nextStage.getCtx()
                )
        );
    }

    @Test
    public void testWithReadTimeout_expectNewTimeout() {
        var nextStage = this.httpBuilder.withReadTimeout(4, TimeUnit.DAYS).and();

        assertAll(
                () -> assertNotNull(nextStage.getMapper()),
                () -> assertEquals(
                        new HttpExecutorContext(
                                new ApiCredentials("test-key"),
                                new HttpTimeouts(
                                        new HttpTimeout(3, TimeUnit.MINUTES),
                                        new HttpTimeout(3, TimeUnit.MINUTES),
                                        new HttpTimeout(4, TimeUnit.DAYS)
                                )
                        ),
                        nextStage.getCtx()
                )
        );
    }

    static class NextStage {
        private final HttpExecutorContext ctx;
        private final ObjectMapper mapper;

        public NextStage(HttpExecutorContext ctx, ObjectMapper mapper) {
            this.ctx = ctx;
            this.mapper = mapper;
        }

        public HttpExecutorContext getCtx() {
            return ctx;
        }

        public ObjectMapper getMapper() {
            return mapper;
        }
    }
}
