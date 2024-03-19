package bg.codexio.ai.openai.api.examples.assistant.ask;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;
import java.io.IOException;

public class AssistantAskImmediate {

    public static void main(String[] args) throws IOException {
        var file = new File(AssistantAskImmediate.class.getClassLoader()
                                                       .getResource("fake-file.txt")
                                                       .getPath());
        var fileDownloadLocation = new File(
                AssistantAskImmediate.class.getClassLoader()
                                           .getResource("")
                                           .getPath() + "generated-files");

        var answer = Threads.defaults()
                            .and()
                            .creating()
                            .deepConfigure()
                            .message()
                            .startWith("You are developer at Codexio.")
                            .attachImmediate(file)
                            .chat()
                            .immediate()
                            .withContent("What is java?")
                            .meta()
                            .awareOf(
                                    "key",
                                    "value"
                            )
                            .file()
                            .feedImmediate(file)
                            .assistant()
                            .assistImmediate(Assistants.defaults()
                                                       .and()
                                                       .poweredByGPT40()
                                                       .from(new CodeInterpreter())
                                                       .called("Cody")
                                                       .instruct("Please focus "
                                                                         + "on "
                                                                         +
                                                                         "explaining"
                                                                         + " the "
                                                                         +
                                                                         "topics as "
                                                                         +
                                                                         "senior "
                                                                         +
                                                                         "developer.")
                                                       .andRespond()
                                                       .immediate()
                                                       .finishRaw())
                            .instruction()
                            .instruct("It would be better to show me some "
                                              + "DevOps skills.")
                            .finish()
                            .immediate()
                            .waitForCompletion()
                            .result()
                            .answersImmediate()
                            .downloadImmediate(fileDownloadLocation);

        System.out.println(answer);
    }
}