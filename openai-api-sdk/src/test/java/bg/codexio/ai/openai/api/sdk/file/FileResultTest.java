package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileResultTest {

    @Test
    void testBuild_expectCorrectResult() {
        var result = FILE_RESULT_BUILDER.build();

        assertEquals(
                FILE_RESULT,
                result
        );
    }

    @Test
    void testDownload_expectCorrectResponse() throws IOException {
        try (
                var downloadUtils = mockStatic(DownloadExecutor.class);
                var mockedFile = mockStatic(Files.class)
        ) {
            executeFileContentResponse();
            HttpBuilder<FileActionTypeStage> httpBuilderMock =
                    mock(HttpBuilder.class);
            mockedFile.when(Files::defaults)
                      .thenReturn(httpBuilderMock);
            mockedFile.when(httpBuilderMock::and)
                      .thenReturn(new FileActionTypeStage(
                              UPLOAD_FILE_HTTP_EXECUTOR,
                              RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
                      ));

            this.downloadMockedExecution(downloadUtils);
            var result = FILE_RESULT.download(TARGET_TEST_FOLDER);
            this.assertDownloadedFile(result);
        }
    }

    @Test
    void testDownload_withAuth_expectCorrectResponse() throws IOException {
        try (
                var downloadUtils = mockStatic(DownloadExecutor.class);
                var mockedFile = mockStatic(Files.class)
        ) {
            executeFileContentResponse();
            HttpBuilder<FileActionTypeStage> httpBuilderMock =
                    mock(HttpBuilder.class);
            mockedFile.when(() -> Files.authenticate((SdkAuth) any()))
                      .thenReturn(httpBuilderMock);
            mockedFile.when(httpBuilderMock::and)
                      .thenReturn(new FileActionTypeStage(
                              UPLOAD_FILE_HTTP_EXECUTOR,
                              RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
                      ));

            this.downloadMockedExecution(downloadUtils);
            var result = FILE_RESULT.download(
                    TARGET_TEST_FOLDER,
                    FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS))
            );
            this.assertDownloadedFile(result);
        }
    }

    @Test
    void testDownload_withHttpExecutorContext_expectCorrectResponse()
            throws IOException {

        try (
                var downloadUtils = mockStatic(DownloadExecutor.class);
                var mockedFile = mockStatic(Files.class)
        ) {
            executeFileContentResponse();
            HttpBuilder<FileActionTypeStage> httpBuilderMock =
                    mock(HttpBuilder.class);
            mockedFile.when(() -> Files.authenticate((HttpExecutorContext) any()))
                      .thenReturn(httpBuilderMock);
            mockedFile.when(httpBuilderMock::and)
                      .thenReturn(new FileActionTypeStage(
                              UPLOAD_FILE_HTTP_EXECUTOR,
                              RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
                      ));

            this.downloadMockedExecution(downloadUtils);
            var result = FILE_RESULT.download(
                    TARGET_TEST_FOLDER,
                    new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS))
            );
            this.assertDownloadedFile(result);
        }
    }

    @Test
    void testDownload_withRetrieveFileContentHttpExecutor_expectCorrectResponse()
            throws IOException {
        try (
                var downloadUtils = mockStatic(DownloadExecutor.class);
                var mockedFile = mockStatic(Files.class)
        ) {
            executeFileContentResponse();
            mockedFile.when(() -> Files.throughHttp(
                              any(),
                              eq(FILE_TEST_ID)
                      ))
                      .thenReturn(new FileDownloadingNameTypeStage<>(
                              RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
                              UploadFileRequest.builder(),
                              FILE_TEST_ID
                      ));
            this.downloadMockedExecution(downloadUtils);

            var result = FILE_RESULT.download(
                    TARGET_TEST_FOLDER,
                    RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR
            );
            this.assertDownloadedFile(result);
        }
    }

    private void downloadMockedExecution(MockedStatic<DownloadExecutor> downloadUtils) {
        downloadUtils.when(() -> DownloadExecutor.downloadTo(
                             any(),
                             any(),
                             any()
                     ))
                     .thenReturn(new File(FILE_TEST_PATH));
    }

    private void assertDownloadedFile(File file) {
        assertEquals(
                FILE_TEST_PATH.replace(
                        "/",
                        File.separator
                ),
                file.getPath()
        );
    }
}