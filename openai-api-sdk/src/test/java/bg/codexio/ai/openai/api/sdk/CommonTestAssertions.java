package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.content.TextContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitationAnnotation;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.file.FileActionTypeStage;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingNameTypeStage;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.MockedStatic;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class CommonTestAssertions {

    public static final String API_CREDENTIALS = "test-key";
    public static final String DEFAULT_ROLE = "user";
    public static final File FILE = new File(
            "src/test/resources/fake-file" + ".txt");
    public static final String[] FILE_IDS_VAR_ARGS = new String[]{
            "test_file_var_arg_id_1", "test_file_var_arg_id_2"
    };
    public static final String[] METADATA_VAR_ARGS = new String[]{
            "metaKey", "metaValue"
    };
    public static final Map<String, String> METADATA_MAP = Map.of(
            METADATA_VAR_ARGS[0],
            METADATA_VAR_ARGS[1]
    );
    public static final String FILE_TEST_ID = "test_id";
    public static final FileResponse FILE_RESPONSE = new FileResponse(
            FILE_TEST_ID,
            "test_object",
            0,
            0,
            "test_name",
            "assistants"
    );
    public static final UploadFileHttpExecutor UPLOAD_FILE_HTTP_EXECUTOR =
            mock(UploadFileHttpExecutor.class);
    public static final RetrieveFileContentHttpExecutor RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR = mock(RetrieveFileContentHttpExecutor.class);

    public static final String THREAD_ID = "thread_test_id";

    public static final ThreadResponse THREAD_RESPONSE = new ThreadResponse(
            THREAD_ID,
            "test_object",
            0,
            METADATA_MAP
    );

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    public static final MessageResult MESSAGE_TEST_RESULT_WITHOUT_IMAGE =
            new MessageResult(
            "test_quote_test_message_value",
            null,
            null
    );

    private CommonTestAssertions() {
    }

    public static void mockFilesToActionTypeStage(
            MockedStatic<Files> mockedFile,
            MockedStatic.Verification verification
    ) {
        HttpBuilder<FileActionTypeStage> httpBuilderMock =
                mock(HttpBuilder.class);
        mockedFile.when(verification)
                  .thenReturn(httpBuilderMock);
        mockedFile.when(httpBuilderMock::and)
                  .thenReturn(new FileActionTypeStage(
                          UPLOAD_FILE_HTTP_EXECUTOR,
                          RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
                  ));
    }

    public static void mockFilesToDownloadingNameTypeStage(MockedStatic<Files> mockedFile) {
        mockedFile.when(() -> Files.throughHttp(
                          any(),
                          (String) any()
                  ))
                  .thenReturn(new FileDownloadingNameTypeStage(
                          RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                          FileDownloadingMeta.builder()
                                             .withFileId(FILE_TEST_ID)
                  ));
    }
}