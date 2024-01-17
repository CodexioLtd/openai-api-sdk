package bg.codexio.ai.openai.api.payload.message.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

public interface MessageResponse
        extends Mergeable<MessageResponse> {
    String object();
}
