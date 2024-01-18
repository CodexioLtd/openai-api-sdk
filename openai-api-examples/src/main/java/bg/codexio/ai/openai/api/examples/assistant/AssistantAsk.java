package bg.codexio.ai.openai.api.examples.assistant;

import bg.codexio.ai.openai.api.examples.file.UploadFile;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class AssistantAsk {

    public static void main(String[] args) {
        var file = new File(UploadFile.class.getClassLoader()
                                            .getResource("fake-file.txt")
                                            .getPath());

        var answer = Threads.defaults()
                            .and()
                            .creating()
                            .message()
                            .startWith("You are developer at Codexio.")
                            .supply(file)
                            .chat()
                            .withContent("Your language of choice is Java.")
                            .meta()
                            .awareOf("key",
                                     "value")
                            .assistant()
                            .assist(Assistants.defaults()
                                              .and()
                                              .poweredByGPT40()
                                              .from(new CodeInterpreter())
                                              .called("Cody")
                                              .instruct("Please focus on "
                                                                + "explaining"
                                                                + " the "
                                                                + "topics as "
                                                                + "senior "
                                                                + "developer.")
                                              .andRespond())
                            .instruction()
                            .instruct("It would be better to show me some "
                                              + "DevOps skills.")
                            .finish()
                            .waitForCompletion()
                            .result()
                            .answers();

        System.out.println(answer);
    }
}
