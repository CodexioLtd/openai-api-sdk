package bg.codexio.ai.openai.api.examples.run;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.util.Objects;

public class CreateRun {

    public static void main(String[] args) throws InterruptedException {
        var defaultThread = Threads.defaults()
                                   .and()
                                   .empty();
        var message = Messages.defaults(defaultThread)
                              .and()
                              .withContent("Since you are my own assistant I "
                                                   + "expect from you to "
                                                   + "tell me more about "
                                                   + "yourself "
                                                   + "and how you can help "
                                                   + "me.");

        var assistant = Assistants.defaults()
                                  .and()
                                  .poweredByGPT40()
                                  .from(new CodeInterpreter())
                                  .called("codexio")
                                  .instruct("You are java developer.")
                                  .andRespond();
        var run = Runnables.defaults(defaultThread)
                           .and()
                           .deepConfigure(assistant)
                           .instruction()
                           .instruct("You are not only java developer, but "
                                             + "you have also some devops "
                                             + "skills.")
                           .andRespond();

        System.out.println(run);
        System.out.println(message);
        System.out.println(defaultThread);
        System.out.println(assistant);
        System.out.println();
        System.out.println("Retrieve run");
        System.out.println();

        var time = 0;
        while (!Objects.equals(
                run.status(),
                "completed"
        )) {
            Thread.sleep(1000);

            run = Runnables.defaults(defaultThread)
                           .and()
                           .from(run);
            time++;
            System.out.println(run);
        }

        System.out.println();
        System.out.println();
        System.out.println("completed run");
        System.out.println(run);
        System.out.println();
        System.out.println("loop executions :" + time);
        System.out.println();
        System.out.println("message object after completion");
        var messageAnswer = Messages.defaults(defaultThread)
                                    .and()
                                    .answers();
        System.out.println(messageAnswer);
    }
}
