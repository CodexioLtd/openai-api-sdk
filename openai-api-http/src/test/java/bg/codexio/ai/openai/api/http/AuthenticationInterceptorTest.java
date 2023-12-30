package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class AuthenticationInterceptorTest {

    private static final String TEST_KEY = "test-Key";
    private static final String TEST_ORGANIZATION = "test-Org";

    private Interceptor.Chain chain;
    private Request builtRequest;

    private static AuthenticationInterceptor setUpWithKey() {
        return new AuthenticationInterceptor(new HttpExecutorContext(new ApiCredentials(TEST_KEY)));
    }

    private static AuthenticationInterceptor setUpWithKeyAndOrganization() {
        return new AuthenticationInterceptor(new HttpExecutorContext(new ApiCredentials(
                TEST_KEY,
                TEST_ORGANIZATION,
                ""
        )));
    }

    @BeforeEach
    public void setUp() throws IOException {
        this.chain = Mockito.spy(Interceptor.Chain.class);
        Mockito.when(this.chain.request())
               .thenReturn(new Request.Builder().url("http://fake")
                                                .build());
        Mockito.when(this.chain.proceed(any()))
               .thenAnswer(answer -> {
                   this.builtRequest = answer.getArgument(0);

                   return new Response.Builder().request(this.builtRequest)
                                                .code(200)
                                                .protocol(Protocol.HTTP_1_1)
                                                .message("OK")
                                                .build();
               });
    }

    @Test
    public void testIntercept_onlyWithKey_shouldHaveBearerTokenAndNoOrganization()
            throws IOException {
        var interceptor = setUpWithKey();

        interceptor.intercept(this.chain);

        assertAll(
                () -> assertEquals(
                        "application/json",
                        this.builtRequest.header("Content-Type")
                ),
                () -> assertEquals(
                        "Bearer test-Key",
                        this.builtRequest.header("Authorization")
                ),
                () -> assertEquals(
                        "",
                        this.builtRequest.header("X-Ignore")
                )
        );
    }

    @Test
    public void testIntercept_withKeyAndOrganization_shouldHaveBearerTokenAndOrganization()
            throws IOException {
        var interceptor = setUpWithKeyAndOrganization();

        interceptor.intercept(this.chain);

        assertAll(
                () -> assertEquals(
                        "application/json",
                        this.builtRequest.header("Content-Type")
                ),
                () -> assertEquals(
                        "Bearer " + TEST_KEY,
                        this.builtRequest.header("Authorization")
                ),
                () -> assertEquals(
                        TEST_ORGANIZATION,
                        this.builtRequest.header("OpenAI-Organization")
                )
        );
    }
}
