package bg.codexio.ai.openai.api.examples.run;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateRunAsync {
    public static void main(String[] args) {

        Runnables.defaults(Threads.defaults()
                                  .and()
                                  .creating()
                                  .empty()
                                  .immediate()
                                  .finish())
                 .and()
                 .initialize(Assistants.defaults()
                                       .and()
                                       .poweredByGPT40()
                                       .from(new CodeInterpreter())
                                       .called("Cody")
                                       .instruct("Be intuitive")
                                       .andRespond()
                                       .immediate()
                                       .finishRaw())
                 .andRespond()
                 .async()
                 .execute()
                 .then(System.out::println);
    }
}
