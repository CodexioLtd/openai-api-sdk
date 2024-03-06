package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateMessageReactive {
    public static void main(String[] args) {
        Messages.defaults(Threads.defaults()
                                 .and()
                                 .creating()
                                 .empty())
                .and()
                .chat()
                .withContent("How are you?")
                .andRespond()
                .reactive()
                .finish()
                .lines()
                .subscribe(System.out::println);
    }
}
