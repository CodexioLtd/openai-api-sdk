package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

import java.util.List;
import java.util.Map;

public record ThreadCreationRequest(
        List<MessageRequest> messages,
        Map<String, String> metadata
)
        implements Streamable,
                   ThreadRequest {

    @Override
    public boolean stream() {
        return false;
    }

}
