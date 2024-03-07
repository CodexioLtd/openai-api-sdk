package bg.codexio.ai.openai.api.examples.thread.modify;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class ModifyThreadReactive {

    public static void main(String[] args) {
        Threads.defaults()
               .and()
               .creating()
               .empty()
               .reactive()
               .finish()
               .flatMap(threadId -> Threads.defaults()
                                           .and()
                                           .modify(threadId)
                                           .withMeta(
                                                   "key1",
                                                   "value1"
                                           )
                                           .andRespond()
                                           .reactive()
                                           .finishRaw()
                                           .response())
               .subscribe(System.out::println);
    }
}
