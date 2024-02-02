package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.message.result.MessageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.FILE;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageResultTest {

    private MessageResult messageResult;
    private FileResult.Builder fileResultBuilder;
    private FileResult fileResult;

    @BeforeEach
    void setUp() {
        this.fileResultBuilder = mock(FileResult.Builder.class);
        this.messageResult = new MessageResult(
                MESSAGE_RESULT_CONTENT_VALUE,
                this.fileResultBuilder,
                null
        );
        this.fileResult = mock(FileResult.class);
        when(this.fileResultBuilder.build()).thenReturn(this.fileResult);
    }

    @Test
    void testEmpty_expectEmptyResult() {
        var emptyMessageResult = MessageResult.empty();

        assertAll(
                () -> assertNull(emptyMessageResult.message()),
                () -> assertNull(emptyMessageResult.fileResult()),
                () -> assertNull(emptyMessageResult.imageFileId())
        );
    }

    @Test
    void testFile_expectCorrectResult() {
        var fileResult = MESSAGE_TEST_RESULT.file();

        assertEquals(
                MESSAGE_FILE_RESULT,
                fileResult
        );
    }

    @Test
    void testDownload_expectCorrectResponse() throws IOException {
        when(this.fileResult.download(any())).thenAnswer(res -> FILE);

        var result = this.messageResult.download(FILE);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withAuth_expectCorrectResponse() throws IOException {
        when(this.fileResult.download(
                any(),
                (SdkAuth) any()
        )).thenAnswer(res -> FILE);

        var result = this.messageResult.download(
                FILE,
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
        );

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withHttpExecutorContext_expectCorrectResponse()
            throws IOException {
        when(this.fileResult.download(
                any(),
                (HttpExecutorContext) any()
        )).thenAnswer(res -> FILE);

        var result = this.messageResult.download(
                FILE,
                new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
        );

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withRetrieveExecutor_expectCorrectResponse()
            throws IOException {
        when(this.fileResult.download(
                any(),
                (RetrieveFileContentHttpExecutor) any()
        )).thenAnswer(res -> FILE);

        var result = this.messageResult.download(
                FILE,
                mock(RetrieveFileContentHttpExecutor.class)
        );

        isMessageResultNotChanged(result);
    }
}