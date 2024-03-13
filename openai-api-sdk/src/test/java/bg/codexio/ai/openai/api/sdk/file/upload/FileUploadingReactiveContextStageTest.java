package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.upload.InternalAssertions.ASSISTANT_PURPOSE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileUploadingReactiveContextStageTest {

    private FileUploadingReactiveContextStage fileUploadingReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.fileUploadingReactiveContextStage =
                new FileUploadingReactiveContextStage(
                UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
                                 .withPurpose(ASSISTANT_PURPOSE_NAME)
        );
    }

    @Test
    public void testFeedRaw_expectCorrectResponse() {
        this.mockReactiveExecution();

        assertEquals(
                FILE_RESPONSE,
                this.fileUploadingReactiveContextStage.feedRaw(FILE)
                                                      .response()
                                                      .block()
        );
    }

    @Test
    public void testFeed_expectCorrectResponse() {
        this.mockReactiveExecution();

        assertEquals(
                FILE_RESPONSE.id(),
                this.fileUploadingReactiveContextStage.feed(FILE)
                                                      .block()
        );
    }

    private void mockReactiveExecution() {
        when(this.fileUploadingReactiveContextStage.executor.executeReactive(any())).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(FILE_RESPONSE)
        ));
    }
}