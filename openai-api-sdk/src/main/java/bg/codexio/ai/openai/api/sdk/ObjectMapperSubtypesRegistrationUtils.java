package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.assistant.tool.AssistantTool;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.util.List;

public class ObjectMapperSubtypesRegistrationUtils {

    public static <I extends Streamable, O extends Mergeable<O>> void registerAssistantTools(
            DefaultOpenAIHttpExecutor<I, O> executor,
            List<AssistantTool> assistantTools
    ) {
        assistantTools.forEach(tool -> executor.configureMappingExternally(objectMapper -> objectMapper.registerSubtypes(new NamedType(
                tool.getClass(),
                tool.type()
        ))));
    }
}
