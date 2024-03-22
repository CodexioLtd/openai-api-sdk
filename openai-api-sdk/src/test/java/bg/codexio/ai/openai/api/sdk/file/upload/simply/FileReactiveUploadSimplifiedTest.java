package bg.codexio.ai.openai.api.sdk.file.upload.simply;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.upload.simply.InternalAssertions.mockFileUploadSimplified;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileReactiveUploadSimplifiedTest {

    @Test
    public void testSimplyRaw_expectCorrectResponse() {
        mockFileUploadSimplified(
                this::mockReactiveExecution,
                () -> {

                    assertNotNull(FileReactiveUploadSimplified.simplyRaw(FILE)
                                                              .block());
                }
        );
    }

    @Test
    public void testSimply_expectCorrectResponse() {
        mockFileUploadSimplified(
                this::mockReactiveExecution,
                () -> {
                    mockReactiveExecution();

                    assertEquals(
                            FILE_RESPONSE.id(),
                            FileReactiveUploadSimplified.simply(FILE)
                                                        .block()
                    );
                }
        );
    }

    private void mockReactiveExecution() {
        when(UPLOAD_FILE_HTTP_EXECUTOR.reactive()
                                      .execute(any())).thenAnswer(res -> new ReactiveHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(FILE_RESPONSE)
        ));
    }
}
