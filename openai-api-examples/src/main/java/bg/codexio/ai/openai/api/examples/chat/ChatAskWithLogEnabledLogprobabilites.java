package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.sdk.chat.Chat;

public class ChatAskWithLogEnabledLogprobabilites {
    public static void main(String[] args) {
        var chatResponse = Chat.defaults()
                               .and()
                               .poweredByGPT40()
                               .deepConfigure()
                               .logprobs()
                               .withTop(3)
                               .and()
                               .done()
                               .andRespond()
                               .immediate()
                               .askRaw("Which is the highest mountain in "
                                               + "Bulgaria?");

        System.out.println(chatResponse);
    }
}
