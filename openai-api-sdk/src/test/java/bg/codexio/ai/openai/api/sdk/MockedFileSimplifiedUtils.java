package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import org.mockito.MockedStatic;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static org.mockito.ArgumentMatchers.any;

public class MockedFileSimplifiedUtils {

    public static void mockImmediateFileSimplified(
            MockedStatic<Authenticator> authUtils,
            MockedStatic<FileImmediateUploadSimplified> filesSimplified
    ) {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));

        authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                 .thenReturn(auth);

        filesSimplified.when(() -> FileImmediateUploadSimplified.simply(FILE))
                       .thenReturn(FILE_RESPONSE.id());
    }

    public static void mockAsyncFileSimplified(
            MockedStatic<Authenticator> authUtils,
            MockedStatic<FileAsyncUploadSimplified> filesSimplified
    ) {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));

        authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                 .thenReturn(auth);

        filesSimplified.when(() -> FileAsyncUploadSimplified.simply(
                               any(),
                               any()
                       ))
                       .thenAnswer(invocation -> {
                           Consumer<String> consumer =
                                   invocation.getArgument(1);
                           consumer.accept(FILE_TEST_ID);

                           return null;
                       });
    }

    public static void mockReactiveFileSimplified(
            MockedStatic<Authenticator> authUtils,
            MockedStatic<FileReactiveUploadSimplified> filesSimplified
    ) {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                 .thenReturn(auth);

        filesSimplified.when(() -> FileReactiveUploadSimplified.simply(any()))
                       .thenAnswer(res -> Mono.justOrEmpty(FILE_TEST_ID));
    }
}