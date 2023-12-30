package bg.codexio.ai.openai.api.sdk;

/**
 * Defines all the ways the underlying runtime should act.
 */
public interface RuntimeSelectionStage {

    RuntimeExecutor immediate();

    RuntimeExecutor async();

    RuntimeExecutor reactive();

}
