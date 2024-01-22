package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

import java.util.Map;

public class InternalAssertions {

    public static final String THREAD_ID = "thread_test_id";

    static final String[] METADATA_ARGS = new String[]{
            "metaKey", "metaValue"
    };
    static final Map<String, String> METADATA = Map.of(
            METADATA_ARGS[0],
            METADATA_ARGS[1]
    );

    public static final ThreadResponse THREAD_RESPONSE = new ThreadResponse(
            THREAD_ID,
            "test_object",
            0,
            METADATA
    );
}
