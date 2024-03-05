package bg.codexio.ai.openai.api.examples.assistant.ask;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class AssistantAskReactive {

    public static void main(String[] args) {
        var file = new File(AssistantAskImmediate.class.getClassLoader()
                                                       .getResource("fake-file.txt")
                                                       .getPath());
        var fileDownloadLocation = new File(
                AssistantAskImmediate.class.getClassLoader()
                                           .getResource("")
                                           .getPath() + "generated-files");

        Threads.defaults()
               .and()
               .creating()
               .deepConfigure()
               .message()
               .startWith("You are developer at Codexio.")
               .attach(file)
               .chat()
               .withContent("Your language of choice is Java.")
               .meta()
               .awareOf(
                       "key",
                       "value"
               )
               .assistant()
               .assist(Assistants.defaults()
                                 .and()
                                 .poweredByGPT40()
                                 .from(new CodeInterpreter())
                                 .called("Cody")
                                 .instruct("Please focus on " + "explaining"
                                                   + " the " + "topics as "
                                                   + "senior " + "developer.")
                                 .andRespond()
                                 .immediate()
                                 .finishRaw())
               .instruction()
               .instruct(
                       "It would be better to show me some " + "DevOps skills.")
               .finishReactiveSimply(fileDownloadLocation)
               .subscribe(System.out::println);
    }
}