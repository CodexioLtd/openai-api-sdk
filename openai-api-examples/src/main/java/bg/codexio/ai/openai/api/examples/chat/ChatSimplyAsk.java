package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.sdk.chat.Chat;

public class ChatSimplyAsk {
    public static void main(String[] args) {
        var response = Chat.simply()
                           .ask("Is Java the coolest programming language on "
                                        + "the planet?");

        System.out.println(response);
    }
}
