package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.content.ImageFileContent;
import bg.codexio.ai.openai.api.payload.message.content.TextContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitationAnnotation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePath;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePathAnnotation;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;

import java.util.Arrays;
import java.util.List;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalAssertions {

    static final MessageHttpExecutor MESSAGE_HTTP_EXECUTOR =
            mock(MessageHttpExecutor.class);

    static final String MESSAGE_CONTENT = "test_message_content";

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

    static void executeWithPathVariable(MessageConfigurationStage messageConfigurationStage) {
        when(messageConfigurationStage.httpExecutor.executeWithPathVariable(
                any(),
                any()
        )).thenAnswer(res -> MESSAGE_RESPONSE_WITH_TEXT_CONTENT);
    }

    static void roleRemainsUnchanged(
            MessageConfigurationStage previousStage,
            MessageConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.role(),
                nextStage.requestBuilder.role()
        );
    }

    static void fileIdsRemainsUnchanged(
            MessageConfigurationStage previousStage,
            MessageConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.fileIds(),
                nextStage.requestBuilder.fileIds()
        );
    }

    static void contentRemainsUnchanged(
            MessageConfigurationStage previousStage,
            MessageConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.content(),
                nextStage.requestBuilder.content()
        );
    }

    static void metadataRemainsUnchanged(
            MessageConfigurationStage previousStage,
            MessageConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.metadata(),
                nextStage.requestBuilder.metadata()
        );
    }
}
