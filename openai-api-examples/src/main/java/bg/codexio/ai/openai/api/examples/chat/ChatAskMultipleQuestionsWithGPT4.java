package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.sdk.chat.Chat;

public class ChatAskMultipleQuestionsWithGPT4 {
    public static void main(String[] args) {
        // In this case, the response for the two questions is going to be
        // merged in one

        var response = Chat.defaults()
                           .and()
                           .poweredByGPT40()
                           .predictable()
                           .andRespond()
                           .immediate()
                           .ask(
                                   "Is Java the coolest programming language "
                                           + "on the planet?",
                                   "Are cats the coolest animals on the "
                                           + "planet too?"
                           );

        System.out.println(response);
    }
}
