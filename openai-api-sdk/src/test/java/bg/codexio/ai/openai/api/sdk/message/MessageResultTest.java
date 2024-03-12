package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.download.DownloadExecutor;
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
    void testDownloadImmediate_expectCorrectResponse() throws IOException {
        this.initializeDefaultMessageResult();

        when(this.fileResult.downloadImmediate(any())).thenAnswer(res -> FILE);

        var result = this.messageResult.downloadImmediate(FILE);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withEmptyMessage_expectDefaultMessageResponse()
            throws IOException {
        this.initializeMessageResultWithoutMessage();

        when(this.fileResult.downloadImmediate(any())).thenAnswer(res -> FILE);

        var result = this.messageResult.downloadImmediate(FILE);

        assertEquals(
                EMPTY,
                result
        );
    }

    @Test
    void testDownloadImmediate_withMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        var result = this.messageResult.downloadImmediate(FILE);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withEmptyFileResult_expectCorrectResponse()
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
            var result = this.messageResult.downloadImmediate(FILE);

            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownloadImmediate_withEmptyFileResultAndNotPresentMessage_expectDefaultMessageResponse()
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
            var result = this.messageResult.downloadImmediate(FILE);

            assertEquals(
                    CREATED_IMAGE_FILE_MESSAGE,
                    result
            );
        }
    }

    @Test
    void testDownloadImmediate_withAuth_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
        mockFileResultDownloadWithAuth(this.fileResult);
        var result = executeDownloadInMessageResultWithAuth(this.messageResult);
        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withAuthAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithoutMessage();
        mockFileResultDownloadWithAuth(this.fileResult);
        var result = executeDownloadInMessageResultWithAuth(this.messageResult);
        assertEquals(
                EMPTY,
                result
        );
    }

    @Test
    void testDownloadImmediate_withAuthAndMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        var result = executeDownloadInMessageResultWithAuth(this.messageResult);
        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withAuthAndEmptyFileResult_expectCorrectResponse()
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
            var result =
                    executeDownloadInMessageResultWithAuth(this.messageResult);
            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownloadImmediate_withAuthAndEmptyFileResultAndNotPresentMessage_expectCorrectResponse()
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
            var result =
                    executeDownloadInMessageResultWithAuth(this.messageResult);
            isDefaultMessageForCreatedImageReturned(result);
        }
    }

    @Test
    void testDownloadImmediate_withHttpExecutorContext_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
        mockFileResultDownloadWithHttpExecutor(this.fileResult);
        var result =
                executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);
        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withHttpExecutorContextAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
        mockFileResultDownloadWithHttpExecutor(this.fileResult);
        var result =
                executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);
        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withHttpExecutorContextWithMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        var result =
                executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withHttpExecutorContextAndEmptyFileResult_expectCorrectResponse()
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
            var result =
                    executeDownloadInMessageResultWithHttpExecutor(this.messageResult);

            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownloadImmediate_withHttpExecutorContextAndEmptyFileResultAndEmptyMessage_expectCorrectResponse()
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
            var result =
                    executeDownloadInMessageResultWithHttpExecutor(this.messageResult);

            isDefaultMessageForCreatedImageReturned(result);
        }
    }


    @Test
    void testDownloadImmediate_withRetrieveExecutor_expectCorrectResponse()
            throws IOException {
        this.initializeDefaultMessageResult();
        mockFileResultDownloadWithRetrieveExecutor(this.fileResult);
        var result =
                executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);

        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withRetrieveExecutorAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithoutMessage();
        mockFileResultDownloadWithRetrieveExecutor(this.fileResult);
        var result =
                executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);

        assertEquals(
                EMPTY,
                result
        );
    }

    @Test
    void testDownloadImmediate_withRetrieveExecutorAndMessageOnly_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithMessageOnly();
        var result =
                executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);
        isMessageResultNotChanged(result);
    }

    @Test
    void testDownloadImmediate_withRetrieveExecutorAndEmptyFileResult_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileId();
        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToDownloadingNameTypeStage(mockedFile);
            var result =
                    executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);
            isMessageResultNotChanged(result);
        }
    }

    @Test
    void testDownloadImmediate_withRetrieveExecutorAndEmptyFileResultAndEmptyMessage_expectCorrectResponse()
            throws IOException {
        this.initializeMessageResultWithImageFileIdAndEmptyMessage();
        try (
                var mockedFile = mockStatic(Files.class);
                var downloadExecutor = mockStatic(DownloadExecutor.class)
        ) {
            mockFilesToDownloadingNameTypeStage(mockedFile);
            var result =
                    executeDownloadInMessageResultWithRetrieveExecutor(this.messageResult);
            isDefaultMessageForCreatedImageReturned(result);
        }
    }

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