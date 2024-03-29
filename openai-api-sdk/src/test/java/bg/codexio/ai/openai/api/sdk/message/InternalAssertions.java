package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.message.content.ImageFileContent;
import bg.codexio.ai.openai.api.payload.message.content.TextContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitationAnnotation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePath;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePathAnnotation;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.file.FileResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.message.constant.MessageConstants.CREATED_IMAGE_FILE_MESSAGE;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalAssertions {

    public static final MessageHttpExecutor MESSAGE_HTTP_EXECUTOR =
            mock(MessageHttpExecutor.class);
    public static final RetrieveListMessagesHttpExecutor RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR = mock(RetrieveListMessagesHttpExecutor.class);
    static final String MESSAGE_CONTENT = "test_message_content";
    static final String MESSAGE_RESULT_CONTENT_VALUE =
            "test_message_test_quote";
    static final MessageResponse MESSAGE_RESPONSE = new MessageResponse(
            "message_test_id",
            "message_test_object",
            0,
            THREAD_ID,
            "message_test_role",
            List.of(
                    new TextMessageContent(new TextContent(
                            "test_message_value",
                            List.of(
                                    new FileCitationAnnotation(
                                            "file_citation",
                                            13,
                                            18,
                                            new FileCitation(
                                                    "test_file_citation_id",
                                                    "test_quote"
                                            )
                                    ),
                                    new FilePathAnnotation(
                                            "random/file/path/test.txt",
                                            0,
                                            0,
                                            new FilePath("file_path_test_id")
                                    )
                            )
                    )),
                    new ImageFileContent("test_file_id")
            ),
            ASSISTANT_ID,
            RUNNABLE_ID,
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE =
            new ListMessagesResponse(
            "list_message_object",
            List.of(MESSAGE_RESPONSE),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    static final MessageResponse MESSAGE_RESPONSE_WITH_TEXT_CONTENT =
            new MessageResponse(
            "message_test_id",
            "message_test_object",
            0,
            THREAD_ID,
            "message_test_role",
            List.of(new TextMessageContent(new TextContent(
                    "test_message_value",
                    List.of(new FileCitationAnnotation(
                            "file_citation",
                            0,
                            0,
                            new FileCitation(
                                    "test_file_citation_id",
                                    "test_quote_"
                            )
                    ))
            ))),
            ASSISTANT_ID,
            RUNNABLE_ID,
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );
    static final MessageResponse MESSAGE_RESPONSE_WITH_IMAGE_CONTENT =
            new MessageResponse(
            "message_test_id",
            "message_test_object",
            0,
            THREAD_ID,
            "message_test_role",
            List.of(new ImageFileContent("test_file_id")),
            ASSISTANT_ID,
            RUNNABLE_ID,
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT = new ListMessagesResponse(
            "list_message_value",
            List.of(MESSAGE_RESPONSE_WITH_IMAGE_CONTENT),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT = new ListMessagesResponse(
            "list_message_object",
            List.of(MESSAGE_RESPONSE_WITH_TEXT_CONTENT),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
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
    static MessageResult MESSAGE_TEST_RESULT_WITHOUT_TEXT = new MessageResult(
            null,
            null,
            "test_file_id"
    );
    static MessageResult MESSAGE_TEST_RESULT_WITHOUT_IMAGE = new MessageResult(
            "test_quote_test_message_value",
            null,
            null
    );

    static <O extends Mergeable<O>> void roleRemainsUnchanged(
            MessageConfigurationStage<O> previousStage,
            MessageConfigurationStage<O> nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.role(),
                nextStage.requestBuilder.role()
        );
    }

    static <O extends Mergeable<O>> void fileIdsRemainsUnchanged(
            MessageConfigurationStage<O> previousStage,
            MessageConfigurationStage<O> nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.fileIds(),
                nextStage.requestBuilder.fileIds()
        );
    }

    static <O extends Mergeable<O>> void contentRemainsUnchanged(
            MessageConfigurationStage<O> previousStage,
            MessageConfigurationStage<O> nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.content(),
                nextStage.requestBuilder.content()
        );
    }

    static <O extends Mergeable<O>> void metadataRemainsUnchanged(
            MessageConfigurationStage<O> previousStage,
            MessageConfigurationStage<O> nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.metadata(),
                nextStage.requestBuilder.metadata()
        );
    }

    static void executeWithPathVariable(MessageConfigurationStage<MessageResponse> messageConfigurationStage) {
        when(messageConfigurationStage.httpExecutor.executeWithPathVariable(
                any(),
                any()
        )).thenAnswer(res -> MESSAGE_RESPONSE_WITH_TEXT_CONTENT);
    }

    static void executeWithPathVariables(
            RetrieveListMessagesHttpExecutor listMessagesHttpExecutor,
            ListMessagesResponse response
    ) {
        when(listMessagesHttpExecutor.executeWithPathVariables(any())).thenAnswer(res -> response);
    }

    static void executeWithPathVariables(MessageConfigurationStage<ListMessagesResponse> messageConfigurationStage) {
        when(messageConfigurationStage.httpExecutor.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT);
    }

    static void mockFileResultDownloadWithAuth(FileResult fileResult)
            throws IOException {
        when(fileResult.download(
                any(),
                (SdkAuth) any()
        )).thenAnswer(res -> FILE);
    }

    static String executeDownloadInMessageResultWithAuth(MessageResult messageResult)
            throws IOException {
        return messageResult.download(
                FILE,
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
        );
    }

    static void mockFileResultDownloadWithHttpExecutor(FileResult fileResult)
            throws IOException {
        when(fileResult.download(
                any(),
                (HttpExecutorContext) any()
        )).thenAnswer(res -> FILE);
    }

    static String executeDownloadInMessageResultWithHttpExecutor(MessageResult messageResult)
            throws IOException {
        return messageResult.download(
                FILE,
                new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
        );
    }

    static void mockFileResultDownloadWithRetrieveExecutor(FileResult fileResult)
            throws IOException {
        when(fileResult.download(
                any(),
                (RetrieveFileContentHttpExecutor) any()
        )).thenAnswer(res -> FILE);
    }

    static String executeDownloadInMessageResultWithRetrieveExecutor(MessageResult messageResult)
            throws IOException {
        return messageResult.download(
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