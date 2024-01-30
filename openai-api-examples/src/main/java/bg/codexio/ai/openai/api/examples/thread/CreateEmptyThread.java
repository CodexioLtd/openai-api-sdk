package bg.codexio.ai.openai.api.examples.thread;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateEmptyThread {

    public static void main(String[] args) {
        var thread = Threads.defaults()
                            .and()
                            .creating()
                            .empty();

        System.out.println(thread);
    }
}