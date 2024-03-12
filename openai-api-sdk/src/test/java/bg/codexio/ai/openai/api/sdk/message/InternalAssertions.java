package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;

import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.message.constant.MessageConstants.CREATED_IMAGE_FILE_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalAssertions {

    static final MessageHttpExecutor MESSAGE_HTTP_EXECUTOR =
            mock(MessageHttpExecutor.class);
    static final RetrieveListMessagesHttpExecutor RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR = mock(RetrieveListMessagesHttpExecutor.class);

    static final String MESSAGE_RESULT_CONTENT_VALUE =
            "test_message_test_quote";

    static final FileResult MESSAGE_FILE_RESULT = new FileResult(
            "file_path_test_id",
            "test.txt"
    );
    static MessageResult MESSAGE_TEST_RESULT = new MessageResult(
            MESSAGE_RESULT_CONTENT_VALUE,
            FileResult.builder()
                      .withId("file_path_test_id")
                      .withFileName("test.txt"),
            "test_file_id"
    );

    static void mockFileResultDownloadWithAuth(FileResult fileResult)
            throws IOException {
        when(fileResult.downloadImmediate(
                any(),
                (SdkAuth) any()
        )).thenAnswer(res -> FILE);
    }

    static String executeDownloadInMessageResultWithAuth(MessageResult messageResult)
            throws IOException {
        return messageResult.downloadImmediate(
                FILE,
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
        );
    }

    static void mockFileResultDownloadWithHttpExecutor(FileResult fileResult)
            throws IOException {
        when(fileResult.downloadImmediate(
                any(),
                (HttpExecutorContext) any()
        )).thenAnswer(res -> FILE);
    }

    static String executeDownloadInMessageResultWithHttpExecutor(MessageResult messageResult)
            throws IOException {
        return messageResult.downloadImmediate(
                FILE,
                new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
        );
    }

    static void mockFileResultDownloadWithRetrieveExecutor(FileResult fileResult)
            throws IOException {
        when(fileResult.downloadImmediate(
                any(),
                (RetrieveFileContentHttpExecutor) any()
        )).thenAnswer(res -> FILE);
    }

    static String executeDownloadInMessageResultWithRetrieveExecutor(MessageResult messageResult)
            throws IOException {
        return messageResult.downloadImmediate(
                FILE,
                mock(RetrieveFileContentHttpExecutor.class)
        );
    }

    static void isMessageResultNotChanged(String result) {
        assertEquals(
                MESSAGE_TEST_RESULT.message(),
                result
        );
    }

    static void isDefaultMessageForCreatedImageReturned(String result) {
        assertEquals(
                CREATED_IMAGE_FILE_MESSAGE,
                result
        );
    }
}