package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.modify.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ThreadModificationReactiveContextTest {

    private ThreadModificationReactiveContextStage threadModificationReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.threadModificationReactiveContextStage =
                new ThreadModificationReactiveContextStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testFinishRaw_expectCorrectResponse() {
        this.mockReactiveExecution();

        assertEquals(
                THREAD_RESPONSE,
                this.threadModificationReactiveContextStage.finishRaw()
                                                           .response()
                                                           .block()
        );
    }

    @Test
    void testFinish_expectCorrectResponse() {
        this.mockReactiveExecution();

        assertEquals(
                THREAD_RESPONSE.id(),
                this.threadModificationReactiveContextStage.finish()
                                                           .block()
        );
    }

    private void mockReactiveExecution() {
        when(this.threadModificationReactiveContextStage.httpExecutor.executeReactiveWithPathVariable(
                any(),
                any()
        )).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(THREAD_RESPONSE)
        ));
    }
}