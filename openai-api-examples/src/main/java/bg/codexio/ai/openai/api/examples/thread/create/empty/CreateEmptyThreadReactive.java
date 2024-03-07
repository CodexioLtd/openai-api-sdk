package bg.codexio.ai.openai.api.examples.thread.create.empty;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateEmptyThreadReactive {
    public static void main(String[] args) {
        Threads.defaults()
               .and()
               .creating()
               .empty()
               .reactive()
               .finishRaw()
               .lines()
               .subscribe(System.out::println);
    }
}