package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateMessage {
    public static void main(String[] args) {
        var messageResponse = Messages.defaults(Threads.defaults()
                                                       .and()
                                                       .empty())
                                      .and()
                                      .withContent("content");

        System.out.println(messageResponse);
    }
}