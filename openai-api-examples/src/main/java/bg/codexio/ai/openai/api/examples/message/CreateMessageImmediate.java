package bg.codexio.ai.openai.api.examples.message;

import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateMessageImmediate {

    public static void main(String[] args) {
        var messageResponse = Messages.defaults(Threads.defaults()
                                                       .and()
                                                       .creating()
                                                       .empty())
                                      .and()
                                      .chat()
                                      .withContent("How are you?")
                                      .andRespond()
                                      .immediate()
                                      .finish();

        System.out.println(messageResponse);
    }
}