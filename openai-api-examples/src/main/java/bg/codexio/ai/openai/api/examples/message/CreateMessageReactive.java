package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.examples.assistant.create.CreateAssistantImmediate;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateMessageReactive {
    public static void main(String[] args) {
        var file = new File(CreateAssistantImmediate.class.getClassLoader()
                                                          .getResource("fake-file.txt")
                                                          .getPath());

        Threads.defaults()
               .and()
               .creating()
               .empty()
               .reactive()
               .finish()
               .flatMap(threadId -> Messages.defaults(threadId)
                                            .and()
                                            .chat()
                                            .withContent("How are you?")
                                            .file()
                                            .feedReactive(file))
               .flatMap(config -> config.andRespond()
                                        .reactive()
                                        .finishRaw()
                                        .response())
               .subscribe(System.out::println);
    }
}
