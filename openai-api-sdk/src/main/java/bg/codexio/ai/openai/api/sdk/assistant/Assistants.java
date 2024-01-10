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

    public static AiModelStage throughHttp(AssistantHttpExecutor httpExecutor) {
        return new AiModelStage(
                httpExecutor,
                AssistantRequest.builder()
        );
    }

    public static HttpBuilder<AiModelStage> authenticate(HttpExecutorContext context) {
        return new HttpBuilder<>(
                context,
                (httpExecutorContext, objectMapper) -> new AiModelStage(
                        new AssistantHttpExecutor(
                                httpExecutorContext,
                                objectMapper
                        ),
                        AssistantRequest.builder()
                )

        );
    }

    public static HttpBuilder<AiModelStage> authenticate(SdkAuth auth) {
        return authenticate(new HttpExecutorContext(auth.credentials()));
    }

    public static HttpBuilder<AiModelStage> defaults() {
        return autoAuthenticate(Assistants::authenticate);
    }
}
