package bg.codexio.ai.openai.api.examples.run;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateRun {

    public static void main(String[] args) {
        var run = Runnables.defaults(Threads.defaults()
                                            .and()
                                            .creating()
                                            .empty())
                           .and()
                           .deepConfigure(Assistants.defaults()
                                                    .and()
                                                    .poweredByGPT40()
                                                    .from(new CodeInterpreter())
                                                    .called("Cody")
                                                    .instruct("Be intuitive")
                                                    .andRespond())
                           .andRespond();

        System.out.println(run);
    }
}