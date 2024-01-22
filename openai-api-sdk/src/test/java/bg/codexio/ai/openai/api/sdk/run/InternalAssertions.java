package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.payload.run.response.action.Function;
import bg.codexio.ai.openai.api.payload.run.response.action.RequiredAction;
import bg.codexio.ai.openai.api.payload.run.response.action.SubmitToolOutput;
import bg.codexio.ai.openai.api.payload.run.response.action.ToolCall;
import bg.codexio.ai.openai.api.payload.run.response.error.LastError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.mockito.Mockito.mock;

public class InternalAssertions {

    static final RunnableHttpExecutor RUNNABLE_HTTP_EXECUTOR =
            mock(RunnableHttpExecutor.class);

    // extract constants like this into class in a different package with
    // public modifier
    static final String API_CREDENTIALS = "test-key";

    static final String RUNNABLE_ID = "runnable_test_id";

    static final ModelType RUNNABLE_MODEL_TYPE = new GPT40Model();

    static final String[] FILE_IDS = new String[]{
            "file_id_test_1", "file_id_test_2"
    };
    static final String[] METADATA_ARGS = new String[]{
            "metaKey", "metaValue"
    };
    static final Map<String, String> RUNNABLE_METADATA = Map.of(
            METADATA_ARGS[0],
            METADATA_ARGS[1]
    );

    static final RunnableResponse RUNNABLE_RESPONSE = new RunnableResponse(
            RUNNABLE_ID,
            "test_object",
            0,
            THREAD_ID,
            ASSISTANT_ID,
            "test_status",
            new RequiredAction(
                    "submit_tool_outputs",
                    new SubmitToolOutput(List.of(new ToolCall(
                            "test_tool_call_id",
                            "test_tool_call_type",
                            new Function(
                                    "test_function_name",
                                    "test_function_arguments"
                            )
                    )))
            ),
            new LastError(
                    "error_test_code",
                    "error_test_message"
            ),
            0,
            0,
            0,
            0,
            0,
            RUNNABLE_MODEL_TYPE.name(),
            "test_instructions",
            List.of(new CodeInterpreter()),
            Arrays.stream(FILE_IDS)
                  .toList(),
            RUNNABLE_METADATA
    );
}