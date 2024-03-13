package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.examples.assistant.create.CreateAssistantImmediate;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateMessageAsync {

    public static void main(String[] args) {
        var file = new File(CreateAssistantImmediate.class.getClassLoader()
                                                          .getResource("fake-file.txt")
                                                          .getPath());

        Threads.defaults()
               .and()
               .creating()
               .empty()
               .async()
               .finishRaw()
               .then(thread -> Messages.defaults(thread)
                                       .and()
                                       .chat()
                                       .withContent("How are you?")
                                       .file()
                                       .feedAsync(
                                               file,
                                               config -> config.andRespond()
                                                               .async()
                                                               .finish()
                                                               .then(System.out::println)
                                       ));

    }
}
