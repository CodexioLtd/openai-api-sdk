package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateMessageReactive {
    public static void main(String[] args) {
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
                                            .andRespond()
                                            .reactive()
                                            .finishRaw()
                                            .response())

               .subscribe(System.out::println);
    }
}
