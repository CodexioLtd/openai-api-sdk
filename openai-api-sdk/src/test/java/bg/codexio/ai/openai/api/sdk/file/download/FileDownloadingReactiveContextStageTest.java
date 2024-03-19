package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.file.download.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileDownloadingReactiveContextStageTest {

    private FileDownloadingReactiveContextStage fileDownloadingReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.fileDownloadingReactiveContextStage =
                new FileDownloadingReactiveContextStage(
                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                FILE_DOWNLOADING_META_WITHOUT_FOLDER
        );
    }

    @Test
    public void testToFolder_expectCorrectResponse() {
        when(this.fileDownloadingReactiveContextStage.executor.executeReactiveWithPathVariables(any())).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(FILE_CONTENT_RESPONSE)
        ));

        mockDownloadExecutor(() -> assertEquals(
                FILE_TEST_PATH.replace(
                        "/",
                        File.separator
                ),
                this.fileDownloadingReactiveContextStage.toFolder(FILE)
                                                        .block()
                                                        .getPath()
        ));
    }
}
