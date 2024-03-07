package bg.codexio.ai.openai.api.examples.thread.create.empty;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateEmptyThreadAsync {

    public static void main(String[] args) {
        Threads.defaults()
               .and()
               .creating()
               .empty()
               .async()
               .finishRaw()
               .then(System.out::println);
    }
}