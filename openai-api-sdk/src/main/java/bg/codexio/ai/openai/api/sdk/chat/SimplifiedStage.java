package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simplified stage where some things
 * such as AI Model Type and creativity
 * are already configured.
 */
public class SimplifiedStage {

    private final ApiCredentials credentials;

    public SimplifiedStage(SdkAuth auth) {
        this.credentials = auth.credentials();
    }

    /**
     * Go ahead to directly ask question by preconfiguring
     * the AI Model Type and the creativity level and receiving a synchronous
     * response
     *
     * @return {@link ChatRuntimeSelectionStage}
     */
    public ImmediateContextStage simply() {
        return Chat.authenticate(new HttpExecutorContext(credentials))
                   .understanding(new ObjectMapper())
                   .poweredByGPT35()
                   .predictable()
                   .andRespond()
                   .immediate();
    }
}
