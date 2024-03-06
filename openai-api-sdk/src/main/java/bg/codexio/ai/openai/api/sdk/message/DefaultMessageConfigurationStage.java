package bg.codexio.ai.openai.api.sdk.message;


public abstract class DefaultMessageConfigurationStage {

    protected final String threadId;

    protected DefaultMessageConfigurationStage(String threadId) {
        this.threadId = threadId;
    }
}