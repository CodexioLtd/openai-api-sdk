package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.chat.Chat;

public class ChatDefinedAuthenticationMethod {
    public static void main(String[] args) {

        // In this case, we're using a predefined SDK Auth, so we skip the
        // iteration through all authentication methods
        // After that, we have some fun by prompting the chat to be as
        // imaginative as possible
        var response = Chat.authenticate(FromJson.AUTH)
                           .and()
                           .poweredByGPT40()
                           .randomized()
                           .assist("You can be as imaginative and dreamy as "
                                           + "possible")
                           .andRespond()
                           .immediate()
                           .ask("Are coffee and cinnamon rolls a good combo?");

        System.out.println(response);
    }
}
