package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.sdk.chat.Chat;

public class ChatAskAsync {
    public static void main(String[] args) {
        Chat.defaults()
            .and()
            .poweredByGPT40()
            .andRespond()
            .async()
            .ask(
                    "Are cinnamon rolls a cool dessert?",
                    "What types of cinnamon rolls exist?"
            )
            .then(System.out::println);
    }
}
