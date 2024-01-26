package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.content.TextContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitationAnnotation;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;

import java.util.Arrays;
import java.util.List;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.FILE_IDS_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalAssertions {

    static final MessageHttpExecutor MESSAGE_HTTP_EXECUTOR =
            mock(MessageHttpExecutor.class);
    static final RetrieveListMessagesHttpExecutor RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR = mock(RetrieveListMessagesHttpExecutor.class);
    static final String MESSAGE_CONTENT = "test_message_content";
    static final MessageResponse MESSAGE_RESPONSE = new MessageResponse(
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
                                    "test_quote"
                            )
                    ))
            ))),
            ASSISTANT_ID,
            RUNNABLE_ID,
            Arrays.stream(FILE_IDS_VAR_ARGS)
                  .toList(),
            METADATA_MAP
    );
    static final ListMessagesResponse LIST_MESSAGE_RESPONSE =
            new ListMessagesResponse(
            "list_message_object",
            List.of(MESSAGE_RESPONSE),
            "list_message_test_first_id",
            "list_message_test_second_id",
            false
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
        )).thenAnswer(res -> MESSAGE_RESPONSE);
    }

    static void executeWithPathVariables(RetrieveListMessagesHttpExecutor listMessagesHttpExecutor) {
        when(listMessagesHttpExecutor.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE);
    }

    static void executeWithPathVariables(MessageConfigurationStage<ListMessagesResponse> messageConfigurationStage) {
        when(messageConfigurationStage.httpExecutor.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE);
    }
}