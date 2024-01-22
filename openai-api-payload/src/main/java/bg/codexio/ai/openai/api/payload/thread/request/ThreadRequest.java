package bg.codexio.ai.openai.api.payload.thread.request;

import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.Map;

public interface ThreadRequest
        extends Streamable {
    Map<String, String> metadata();
}