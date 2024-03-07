package bg.codexio.ai.openai.api.examples.thread.create.empty;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

public class CreateEmptyThreadImmediate {

    public static void main(String[] args) {
        var thread = Threads.defaults()
                            .and()
                            .creating()
                            .empty()
                            .immediate()
                            .finishRaw();

        System.out.println(thread);
    }
}