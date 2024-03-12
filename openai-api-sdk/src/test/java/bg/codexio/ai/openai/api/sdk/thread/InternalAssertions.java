package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;

import static org.mockito.Mockito.mock;

public class InternalAssertions {

    static final CreateThreadHttpExecutor CREATE_THREAD_HTTP_EXECUTOR =
            mock(CreateThreadHttpExecutor.class);

    static final ModifyThreadHttpExecutor MODIFY_THREAD_HTTP_EXECUTOR =
            mock(ModifyThreadHttpExecutor.class);
}