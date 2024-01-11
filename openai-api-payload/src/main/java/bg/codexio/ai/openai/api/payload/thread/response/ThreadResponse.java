package bg.codexio.ai.openai.api.payload.thread.response;

import bg.codexio.ai.openai.api.payload.Mergeable;

import java.util.Map;

public record ThreadResponse(
        String id,
        String object,
        Integer createdAt,
        Map<String, String> metadata
)
        implements Mergeable<ThreadResponse> {
    @Override
    public ThreadResponse merge(ThreadResponse other) {
        if (other == null) {
            return this;
        }

        return null;
    }
}