package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.Map;

public record ThreadModificationRequest(
        Map<String, String> metadata
)
        implements Streamable,
                   ThreadRequest {
    @Override
    public boolean stream() {
        return false;
    }
}
