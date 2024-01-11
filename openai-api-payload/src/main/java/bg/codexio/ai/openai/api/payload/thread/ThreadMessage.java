package bg.codexio.ai.openai.api.payload.thread;

import java.util.HashMap;
import java.util.List;

public record ThreadMessage(
        String message,
        String content,
        List<String> fileIds,
        HashMap<String, String> metadata
) {}
