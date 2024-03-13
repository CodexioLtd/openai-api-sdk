package bg.codexio.ai.openai.api.examples.assistant.create;

import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.assistant.tool.Retrieval;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;

import java.io.File;

public class CreateAssistantImmediate {

    public static void main(String[] args) {
        var file = new File(CreateAssistantImmediate.class.getClassLoader()
                                                          .getResource("fake-file.txt")
                                                          .getPath());

        var assistant = Assistants.defaults()
                                  .and()
                                  .turboPowered()
                                  .from(
                                          new CodeInterpreter(),
                                          new Retrieval()
                                  )
                                  .called("Codexio")
                                  .instruct("You are the best java developer,"
                                                    + " you are going to "
                                                    + "participate in new "
                                                    + "interesting projects.")

                                  .meta()
                                  .awareOf(
                                          "key1",
                                          "value1",
                                          "key2",
                                          "value2"
                                  )
                                  .file()
                                  .feedImmediate(file)
                                  .andRespond()
                                  .immediate()
                                  .finishRaw();

        System.out.println(assistant);
    }
}