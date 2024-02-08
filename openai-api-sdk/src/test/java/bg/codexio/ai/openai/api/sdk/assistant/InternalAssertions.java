package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;

import java.util.Arrays;
import java.util.List;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InternalAssertions {

    public static final String ASSISTANT_ID = "assistant_test_id";

    static final AssistantHttpExecutor ASSISTANT_HTTP_EXECUTOR =
            mock(AssistantHttpExecutor.class);

    static final ModelType ASSISTANT_MODEL_TYPE = new GPT40Model();
    static final String CODE_INTERPRETER_TYPE = "code_interpreter";
    static final String RETRIEVAL_TOOL_TYPE = "retrieval";
    static final String ASSISTANT_NAME = "test_name";
    static final String ASSISTANT_INSTRUCTION = "test_instruction";
    static final String ASSISTANT_DESCRIPTION = "test_description";
    static final String[] FILE_IDS = new String[]{
            "file_id_test_1", "file_id_test_2"
    };

    public static final AssistantResponse ASSISTANT_RESPONSE =
            new AssistantResponse(
            ASSISTANT_ID,
            "test_object",
            0,
            ASSISTANT_NAME,
            ASSISTANT_DESCRIPTION,
            ASSISTANT_MODEL_TYPE.name(),
            ASSISTANT_INSTRUCTION,
            List.of(new CodeInterpreter()),
            Arrays.stream(FILE_IDS)
                  .toList(),
            METADATA_MAP
    );

    static void modelRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.model(),
                nextStage.requestBuilder.model()
        );
    }

    static void nameRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.name(),
                nextStage.requestBuilder.name()
        );
    }

    static void descriptionRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.description(),
                nextStage.requestBuilder.description()
        );
    }

    static void toolsRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.tools(),
                nextStage.requestBuilder.tools()
        );
    }

    static void instructionsRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.instructions(),
                nextStage.requestBuilder.instructions()
        );
    }

    static void fileIdsRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.fileIds(),
                nextStage.requestBuilder.fileIds()
        );
    }

    static void metadataRemainsUnchanged(
            AssistantConfigurationStage previousStage,
            AssistantConfigurationStage nextStage
    ) {
        assertEquals(
                previousStage.requestBuilder.metadata(),
                nextStage.requestBuilder.metadata()
        );
    }
}