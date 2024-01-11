package bg.codexio.ai.openai.api.examples.assistant;

import bg.codexio.ai.openai.api.examples.file.UploadFile;
import bg.codexio.ai.openai.api.payload.assistant.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.assistant.Retrieval;
import bg.codexio.ai.openai.api.sdk.assistant.Assistants;

import java.io.File;

public class CreateAssistant {

    public static void main(String[] args) {
        var file = new File(UploadFile.class.getClassLoader()
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
                                  .instructToMetadata(
                                          "You are the best java developer,"
                                                  + " you are going to "
                                                  + "participate in new "
                                                  + "interesting projects.")
                                  .awareOfToFile("key1",
                                                 "value1",
                                                 "key2",
                                                 "value2"
                                  )
                                  .feed(file);

        System.out.println(assistant);
    }
}
