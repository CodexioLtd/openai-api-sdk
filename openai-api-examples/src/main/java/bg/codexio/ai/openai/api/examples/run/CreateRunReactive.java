package bg.codexio.ai.openai.api.examples.run;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateRunReactive {

    public static void main(String[] args) {
        Runnables.defaults(Threads.defaults()
                                  .and()
                                  .creating()
                                  .empty())
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
                 .reactive()
                 .executeRaw()
                 .lines()
                 .subscribe(System.out::println);
    }
}