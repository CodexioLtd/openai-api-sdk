package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ThreadReactiveContextStageTest {

    private ThreadReactiveContextStage threadReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.threadReactiveContextStage = new ThreadReactiveContextStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );
    }

    @Test
    void testFinishRaw_expectCorrectResponse() {
        this.mockReactiveExecution();

        assertEquals(
                THREAD_RESPONSE,
                this.threadReactiveContextStage.finishRaw()
                                               .response()
                                               .block()
        );
    }

    @Test
    void testFinish_expectCorrectResponse() {
        this.mockReactiveExecution();

        assertEquals(
                THREAD_RESPONSE.id(),
                this.threadReactiveContextStage.finish()
                                               .block()
        );
    }

    private void mockReactiveExecution() {
        when(this.threadReactiveContextStage.httpExecutor.executeReactive(any())).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(THREAD_RESPONSE)
        ));
    }
}
