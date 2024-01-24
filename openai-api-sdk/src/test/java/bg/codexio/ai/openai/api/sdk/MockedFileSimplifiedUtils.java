package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.sdk.file.FileSimplified;
import bg.codexio.ai.openai.api.sdk.file.FileTargetingStage;
import org.mockito.MockedStatic;

import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.FILE;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static org.mockito.ArgumentMatchers.any;

public class MockedFileSimplifiedUtils {
    public static void mockFileSimplified(
            MockedStatic<Authenticator> authUtils,
            HttpBuilder<FileTargetingStage> auth,
            MockedStatic<FileSimplified> filesSimplified
    ) {
        authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                 .thenReturn(auth);

        filesSimplified.when(() -> FileSimplified.simply(FILE))
                       .thenReturn(FILE_RESPONSE.id());
    }
}
