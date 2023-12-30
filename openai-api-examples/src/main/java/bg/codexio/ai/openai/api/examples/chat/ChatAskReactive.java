package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.sdk.chat.Chat;

public class ChatAskReactive {
    public static void main(String[] args) {
        Chat.defaults()
            .and()
            .poweredByGPT40()
            .creativeAs(Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY)
            .andRespond()
            .reactive()
            .askRaw(
                    "Are cinnamon rolls a cool dessert?",
                    "What types of cinnamon rolls exist?"
            )
            .lines()
            .subscribe(System.out::println);
    }
}
