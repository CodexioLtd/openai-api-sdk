package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.content.ImageFileContent;
import bg.codexio.ai.openai.api.payload.message.content.TextContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitationAnnotation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePath;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePathAnnotation;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE_IDS_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalAssertions {
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT = new ListMessagesResponse(
            "list_message_value",
            List.of(new MessageResponse(
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
            )),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION = new ListMessagesResponse(
            "list_message_object",
            List.of(new MessageResponse(
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
            )),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_PATH = new ListMessagesResponse(
            "list_message_object",
            List.of(new MessageResponse(
                    "message_test_id",
                    "message_test_object",
                    0,
                    THREAD_ID,
                    "message_test_role",
                    List.of(new TextMessageContent(new TextContent(
                            "test_message_value",
                            List.of(new FilePathAnnotation(
                                    "file_path/test_name",
                                    0,
                                    0,
                                    new FilePath("test_file_path_id")
                            ))
                    ))),
                    ASSISTANT_ID,
                    RUNNABLE_ID,
                    Arrays.stream(FILE_IDS_VAR_ARGS)
                          .toList(),
                    METADATA_MAP
            )),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITH_EMPTY_CONTENT = new ListMessagesResponse(
            "list_message_value",
            List.of(new MessageResponse(
                    "message_test_id",
                    "message_test_object",
                    0,
                    THREAD_ID,
                    "message_test_role",
                    new ArrayList<>(),
                    ASSISTANT_ID,
                    RUNNABLE_ID,
                    Arrays.stream(FILE_IDS_VAR_ARGS)
                          .toList(),
                    METADATA_MAP
            )),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    public static final ListMessagesResponse LIST_MESSAGE_RESPONSE_WITHOUT_DATA = new ListMessagesResponse(
            "list_message_object",
            null,
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    public static final ListMessagesResponse LIST_MESSAGES_RESPONSE_WITH_EMPTY_DATA = new ListMessagesResponse(
            "list_message_object",
            new ArrayList<>(),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
    );
    static final RetrieveListMessagesHttpExecutor RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR = mock(RetrieveListMessagesHttpExecutor.class);
    static final String MESSAGE_RESULT_CONTENT_VALUE =
            "test_message_test_quote";
    static final ListMessagesResponse LIST_MESSAGE_RESPONSE =
            new ListMessagesResponse(
            "list_message_object",
            List.of(new MessageResponse(
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
                                                    new FilePath(
                                                            "file_path_test_id")
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
            )),
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
    static MessageResult MESSAGE_TEST_RESULT_WITH_FILE_RESULT =
            new MessageResult(
            "test_message_value",
            FileResult.builder()
                      .withId("test_file_path_id")
                      .withFileName("test_name"),
            null
    );

    static void executeWithPathVariables(
            RetrieveListMessagesHttpExecutor listMessagesHttpExecutor,
            ListMessagesResponse response
    ) {
        when(listMessagesHttpExecutor.executeWithPathVariables(any())).thenAnswer(res -> response);
    }

    static void executeWithPathVariables(RetrieveListMessagesHttpExecutor messageConfigurationStage) {
        when(messageConfigurationStage.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION);
    }
}