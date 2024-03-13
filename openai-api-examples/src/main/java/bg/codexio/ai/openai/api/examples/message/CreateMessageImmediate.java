package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.examples.assistant.create.CreateAssistantImmediate;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateMessageImmediate {

    public static void main(String[] args) {
        var file = new File(CreateAssistantImmediate.class.getClassLoader()
                                                          .getResource("fake-file.txt")
                                                          .getPath());

        var messageResponse = Messages.defaults(Threads.defaults()
                                                       .and()
                                                       .creating()
                                                       .empty()
                                                       .immediate()
                                                       .finish())
                                      .and()
                                      .chat()
                                      .withContent("How are you?")
                                      .file()
                                      .feedImmediate(file)
                                      .andRespond()
                                      .immediate()
                                      .finish();

        System.out.println(messageResponse);
    }
}