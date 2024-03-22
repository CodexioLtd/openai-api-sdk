package bg.codexio.ai.openai.api.sdk.file.upload;

import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockExecution;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.upload.InternalAssertions.ASSISTANT_PURPOSE_NAME;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileUploadingAsyncPromiseStageTest {

    private FileUploadingAsyncPromise fileUploadingAsyncPromise;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.fileUploadingAsyncPromise = new FileUploadingAsyncPromise(
                UPLOAD_FILE_HTTP_EXECUTOR,
                UploadFileRequest.builder()
                                 .withPurpose(ASSISTANT_PURPOSE_NAME)
        );

        mockExecution(
                UPLOAD_FILE_HTTP_EXECUTOR,
                FILE_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(FILE_RESPONSE)
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new FileConsumer());
        this.fileUploadingAsyncPromise.then(finalizer);

        verify(finalizer).accept(FILE_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.fileUploadingAsyncPromise.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new FileConsumer());

        this.fileUploadingAsyncPromise.then(
                callBack,
                finalizer
        );

        assertAll(
                () -> verify(
                        callBack,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(FILE_RESPONSE)
        );
    }
}

class FileConsumer
        implements Consumer<FileResponse> {
    @Override
    public void accept(FileResponse fileResponse) {

    }
}
