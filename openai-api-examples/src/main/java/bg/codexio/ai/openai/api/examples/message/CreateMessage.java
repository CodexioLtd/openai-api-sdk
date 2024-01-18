package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateMessage {
    public static void main(String[] args) {
        var messageResponse = Messages.defaults(Threads.defaults()
                                                       .and()
                                                       .empty())
                                      .and()
                                      .chat()
                                      .withContent("How are you?")
                                      .andRespond();

        System.out.println(messageResponse);
    }
}