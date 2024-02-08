package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.DownloadExecutor;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.message.constant.MessageConstants.CREATED_IMAGE_FILE_MESSAGE;
import static bg.codexio.ai.openai.api.sdk.message.constant.MessageConstants.EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageResultTest {

    private MessageResult messageResult;

    private FileResult fileResult;

    private FileResult.Builder fileResultBuilder;

    @BeforeEach
    void setUp() {
        this.fileResultBuilder = mock(FileResult.Builder.class);
        this.fileResult = mock(FileResult.class);
        when(fileResultBuilder.build()).thenReturn(this.fileResult);
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
        this.initializeDefaultMessageResult();

        when(this.fileResult.download(any())).thenAnswer(res -> FILE);

        var result = this.messageResult.download(FILE);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withEmptyMessage_expectDefaultMessageResponse()
            throws IOException {
        this.initializeMessageResultWithoutMessage();

        when(this.fileResult.download(any())).thenAnswer(res -> FILE);

        var result = this.messageResult.download(FILE);

        assertEquals(
                EMPTY,
                result
        );
    }

    @Test
    void testDownload_withEmptyFileResult_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileId();

        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToActionTypeStage(
                    mockedFile,
                    Files::defaults
            );
            var result = this.messageResult.download(FILE);

            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownload_withEmptyFileResultAndNotPresentMessage_expectDefaultMessageResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileIdAndEmptyMessage();

        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToActionTypeStage(
                    mockedFile,
                    Files::defaults
            );
            var result = this.messageResult.download(FILE);

            assertEquals(
                    CREATED_IMAGE_FILE_MESSAGE,
                    result
            );
        }
    }

    @Test
    void testDownload_withMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        var result = this.messageResult.download(FILE);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withAuth_expectCorrectResponse() throws IOException {
        this.initializeDefaultMessageResult();
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
    void testDownload_withAuthAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithoutMessage();
        this.messageResult = new MessageResult(
                null,
                this.fileResultBuilder,
                null
        );
        when(this.fileResult.download(
                any(),
                (SdkAuth) any()
        )).thenAnswer(res -> FILE);

        var result = this.messageResult.download(
                FILE,
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
        );

        assertEquals(
                EMPTY,
                result
        );
    }

    @Test
    void testDownload_withAuthAndMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();

        var result = this.messageResult.download(
                FILE,
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
        );

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withAuthAndEmptyFileResult_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileId();

        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToActionTypeStage(
                    mockedFile,
                    () -> Files.authenticate((SdkAuth) any())
            );
            var result = this.messageResult.download(
                    FILE,
                    FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
            );

            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownload_withAuthAndEmptyFileResultAndNotPresentMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileIdAndEmptyMessage();

        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToActionTypeStage(
                    mockedFile,
                    () -> Files.authenticate((SdkAuth) any())
            );
            var result = this.messageResult.download(
                    FILE,
                    FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
            );

            assertEquals(
                    CREATED_IMAGE_FILE_MESSAGE,
                    result
            );
        }
    }

    @Test
    void testDownload_withHttpExecutorContext_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
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
    void testDownload_withHttpExecutorContextAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
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
    void testDownload_withHttpExecutorContextWithMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        var result = this.messageResult.download(
                FILE,
                new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
        );

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownload_withHttpExecutorContextAndEmptyFileResult_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileId();
        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToActionTypeStage(
                    mockedFile,
                    () -> Files.authenticate((HttpExecutorContext) any())
            );
            var result = this.messageResult.download(
                    FILE,
                    new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
            );

            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownload_withHttpExecutorContextAndEmptyFileResultAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileIdAndEmptyMessage();
        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToActionTypeStage(
                    mockedFile,
                    () -> Files.authenticate((HttpExecutorContext) any())
            );
            var result = this.messageResult.download(
                    FILE,
                    new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
            );

            assertEquals(
                    CREATED_IMAGE_FILE_MESSAGE,
                    result
            );
        }
    }


    @Test
    void testDownload_withRetrieveExecutor_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
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

    @Test
    void testDownload_withRetrieveExecutorAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithoutMessage();
        when(this.fileResult.download(
                any(),
                (RetrieveFileContentHttpExecutor) any()
        )).thenAnswer(res -> FILE);

        var result = this.messageResult.download(
                FILE,
                mock(RetrieveFileContentHttpExecutor.class)
        );

        assertEquals(
                EMPTY,
                result
        );
    }

    @Test
    void testDownload_withRetrieveExecutorAndMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        this.messageResult = new MessageResult(
                MESSAGE_RESULT_CONTENT_VALUE,
                null,
                null
        );

        var result = this.messageResult.download(
                FILE,
                mock(RetrieveFileContentHttpExecutor.class)
        );

        isMessageResultNotChanged(result);
    }

    //    @Test
    //    void
    //    testDownload_withRetrieveExecutorAndEmptyFileResult_expectCorrectResponse()
    //            throws IOException {
    //        this.initializeMessageResultWithImageFileId();
    //        try (
    //                var mockedFile = mockStatic(Files.class);
    //                var downloadExecutor = mockStatic(DownloadExecutor.class)
    //        ) {
    //            mockFilesToDownloadingNameTypeStage(mockedFile);
    //            var result = this.messageResult.download(
    //                    FILE,
    //                    mock(RetrieveFileContentHttpExecutor.class)
    //            );
    //
    //            isMessageResultNotChanged(result);
    //        }
    //    }

    private void initializeDefaultMessageResult() {
        this.messageResult = new MessageResult(
                MESSAGE_RESULT_CONTENT_VALUE,
                fileResultBuilder,
                null
        );
    }

    private void initializeMessageResultWithoutMessage() {
        this.messageResult = new MessageResult(
                null,
                fileResultBuilder,
                null
        );
    }

    private void initializeMessageResultWithImageFileId() {
        this.messageResult = new MessageResult(
                MESSAGE_RESULT_CONTENT_VALUE,
                null,
                "test_image_file_id"
        );
    }

    private void initializeMessageResultWithImageFileIdAndEmptyMessage() {
        this.messageResult = new MessageResult(
                null,
                null,
                "test_image_file_id"
        );
    }

    private void initializeMessageResultWithMessageOnly() {
        this.messageResult = new MessageResult(
                MESSAGE_RESULT_CONTENT_VALUE,
                null,
                null
        );
    }
}