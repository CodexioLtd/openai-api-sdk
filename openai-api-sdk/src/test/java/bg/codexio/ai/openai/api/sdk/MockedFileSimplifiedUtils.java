package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.Files;

import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.FILE;
import static bg.codexio.ai.openai.api.sdk.file.FilesTest.TEST_KEY;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_SIMPLIFIED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class MockedFileSimplifiedUtils {
    public static Authenticator authenticateAndMockFilesSimplified() {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(TEST_KEY)));

        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = FILE_SIMPLIFIED
        ) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            filesSimplified.when(() -> Files.defaults()
                                            .and()
                                            .forAssistants()
                                            .feed(FILE))
                           .thenReturn(FILE_RESPONSE.id());

            // return auth;
        }
        return null;
    }
}
