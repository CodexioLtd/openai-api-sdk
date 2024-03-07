package bg.codexio.ai.openai.api.examples.thread.modify;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class ModifyThreadAsync {

    public static void main(String[] args) {
        Threads.defaults()
               .and()
               .creating()
               .empty()
               .async()
               .finishRaw()
               .then(thread -> Threads.defaults()
                                      .and()
                                      .modify(thread)
                                      .withMeta(
                                              "key1",
                                              "value1"
                                      )
                                      .andRespond()
                                      .async()
                                      .finishRaw()
                                      .then(System.out::println));
    }
}