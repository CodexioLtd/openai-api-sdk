package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

import static bg.codexio.ai.openai.api.sdk.Authenticator.autoAuthenticate;

public class Assistants {

    private Assistants() {
    }

    public static AIModelStage throughHttp(AssistantHttpExecutor httpExecutor) {
        return new AIModelStage(
                httpExecutor,
                AssistantRequest.builder()
        );
    }

    public static HttpBuilder<AIModelStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new AIModelStage(
                        new AssistantHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        AssistantRequest.builder()
                )

        );
    }

    public static HttpBuilder<AIModelStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    public static HttpBuilder<AIModelStage> defaults() {
        return autoAuthenticate(Assistants::authenticate);
    }
}