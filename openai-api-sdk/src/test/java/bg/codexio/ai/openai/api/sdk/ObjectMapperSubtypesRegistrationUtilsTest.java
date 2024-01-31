package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ObjectMapperSubtypesRegistrationUtilsTest {

    @Test
    void testRegisterAssistantTools() {
        DefaultOpenAIHttpExecutor<?, ?> executor =
                mock(DefaultOpenAIHttpExecutor.class);
        ObjectMapperSubtypesRegistrationUtils.registerAssistantTools(
                executor,
                List.of(new CodeInterpreter())
        );

        verify(executor).configureMappingExternally(any());
    }
}