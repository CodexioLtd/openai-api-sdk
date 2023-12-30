package bg.codexio.ai.openai.api.sdk;

/**
 * Stages which provide the ability to bypass further configurations
 * and go to selecting a runtime. This naturally applies to the last
 * stage in the chain as well.
 */
public interface TerminalStage {

    RuntimeSelectionStage andRespond();

}
