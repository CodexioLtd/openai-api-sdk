package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateMessageAsync {

    public static void main(String[] args) {
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
                                       .andRespond()
                                       .async()
                                       .finish()
                                       .then(System.out::println));

    }
}