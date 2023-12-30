package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.chat.Chat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.TimeUnit;

public class ChatAskWithCustomHttpClientHttpContext {
    public static void main(String[] args) {
        // HTTP context with changed timeouts and predefined SDK Auth
        var ctx =
                new HttpExecutorContext(FromJson.AUTH.credentials()).withCallTimeout(
                                                                              30,
                                                                              TimeUnit.SECONDS
                                                                      )
                                                                      .withConnectTimeout(
                                                                              20,
                                                                              TimeUnit.SECONDS
                                                                      )
                                                                      .withReadTimeout(
                                                                              10,
                                                                              TimeUnit.SECONDS
                                                                      );

        // ObjectMapper with additional configurations
        // Don't recommend to change the transformation of the cases though
        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        var executor = new ChatHttpExecutor(
                ctx,
                mapper
        );

        var response = Chat.throughHttp(executor)
                           .poweredByGPT40()
                           .predictable()
                           .andRespond()
                           .immediate()
                           .ask("Are cats the coolest animals on the planet "
                                        + "too?");

        System.out.println(response);
    }
}
