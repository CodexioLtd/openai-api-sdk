package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

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
            mockFilesToActionTypeStage(
                    mockedFile,
                    Files::defaults
            );

            this.downloadMockedExecution(downloadUtils);
            var result = FILE_RESULT.downloadImmediate(FILE);
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
            mockFilesToActionTypeStage(
                    mockedFile,
                    () -> Files.authenticate((SdkAuth) any())
            );

            this.downloadMockedExecution(downloadUtils);
            var result = FILE_RESULT.downloadImmediate(
                    FILE,
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
            mockFilesToActionTypeStage(
                    mockedFile,
                    () -> Files.authenticate((HttpExecutorContext) any())
            );

            this.downloadMockedExecution(downloadUtils);
            var result = FILE_RESULT.downloadImmediate(
                    FILE,
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
            mockFilesToDownloadingNameTypeStage(mockedFile);
            this.downloadMockedExecution(downloadUtils);

            var result = FILE_RESULT.downloadImmediate(
                    FILE,
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