package bg.codexio.ai.openai.api.examples.thread.modify;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class ModifyThreadImmediate {

    public static void main(String[] args) {
        var thread = Threads.defaults()
                            .and()
                            .modify(Threads.defaults()
                                           .and()
                                           .creating()
                                           .empty()
                                           .immediate()
                                           .finish())
                            .withMeta(
                                    "key1",
                                    "value1"
                            )
                            .andRespond()
                            .immediate()
                            .finishRaw();

        System.out.println(thread);
    }
}