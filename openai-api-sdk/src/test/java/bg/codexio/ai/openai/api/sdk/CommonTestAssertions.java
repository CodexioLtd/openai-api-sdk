package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.http.file.UploadFileHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.file.FileActionTypeStage;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.download.FileDownloadingNameTypeStage;
import org.mockito.MockedStatic;

import java.io.File;
import java.util.Map;

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
    public static final UploadFileHttpExecutor UPLOAD_FILE_HTTP_EXECUTOR =
            mock(UploadFileHttpExecutor.class);
    public static final RetrieveFileContentHttpExecutor RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR = mock(RetrieveFileContentHttpExecutor.class);

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
                  .thenReturn(new FileDownloadingNameTypeStage<>(
                          RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                          UploadFileRequest.builder(),
                          FILE_TEST_ID
                  ));
    }
}