package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.Images.*;
import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class ImagesTest {

    @Test
    public void testThroughHttp_withCreateExecutor_expectCreateExecutor() {
        var nextStage = throughHttp(CREATE_IMAGE_HTTP_EXECUTOR);

        assertAll(() -> assertEquals(
                CREATE_IMAGE_HTTP_EXECUTOR,
                nextStage.createExecutor
        ));
    }

    @Test
    public void testThroughHttp_withEditExecutor_expectEditExecutor() {
        var nextStage = throughHttp(EDIT_IMAGE_HTTP_EXECUTOR);

        assertAll(() -> assertEquals(
                EDIT_IMAGE_HTTP_EXECUTOR,
                nextStage.editExecutor
        ));
    }

    @Test
    public void testThroughHttp_withVariationsExecutor_expectVariationsExecutor() {
        var nextStage = throughHttp(IMAGE_VARIATION_HTTP_EXECUTOR);

        assertAll(() -> assertEquals(
                IMAGE_VARIATION_HTTP_EXECUTOR,
                nextStage.variationExecutor
        ));

    }

    @Test
    public void testAuthenticate_withCredentials_expectNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials("test-key"));
        var httpBuilder = authenticate(ctx);

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.createExecutor),
                () -> assertNotNull(nextStage.editExecutor),
                () -> assertNotNull(nextStage.variationExecutor)
        );
    }

    @Test
    public void testAuthenticate_withAuth_expectHttpBuilderAndNextStage() {
        var httpBuilder =
                authenticate(FromDeveloper.doPass(new ApiCredentials("test-key")));

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.createExecutor),
                () -> assertNotNull(nextStage.editExecutor),
                () -> assertNotNull(nextStage.variationExecutor)
        );
    }

    @Test
    public void testSimply_withAuth_expectNextStage() {
        var nextStage = simply(FromDeveloper.doPass(new ApiCredentials("test-key")));

        assertNotNull(nextStage);
    }

    @Test
    public void testDefaults_expectBuilder() {
        var auth = authenticate(FromDeveloper.doPass(new ApiCredentials("test-key")));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults();

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertAll(
                    () -> assertNotNull(nextStage.createExecutor),
                    () -> assertNotNull(nextStage.editExecutor),
                    () -> assertNotNull(nextStage.variationExecutor)
            );
        }
    }

    @Test
    public void testSimply_expectNextStage() {
        var auth = simply(FromDeveloper.doPass(new ApiCredentials("test-key")));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var nextStage = simply();

            assertNotNull(nextStage);
        }
    }
}
